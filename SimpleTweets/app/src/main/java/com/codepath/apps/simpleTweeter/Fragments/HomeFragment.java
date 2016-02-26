package com.codepath.apps.simpleTweeter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.simpleTweeter.Activities.TimelinesActivity;
import com.codepath.apps.simpleTweeter.EndlessRecyclerOnScrollListener;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.TweeterClient;
import com.codepath.apps.simpleTweeter.TwitterApplication;
import com.codepath.apps.simpleTweeter.adapters.TweetAdapter;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rakhe on 2/23/2016.
 */
public class HomeFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static HomeFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    TweeterClient client;
    ArrayList<Tweet> tweets;
    TweetAdapter tweetAdapter;
    RecyclerView rvTweets;
    boolean inCall;
    private SwipeRefreshLayout mSwipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        // Setup handles to view objects here
        // etFoo = (EditText) view.findViewById(R.id.etFoo);
        client = TwitterApplication.getRestClient();
        rvTweets = (RecyclerView) view.findViewById(R.id.rvTweets);
        tweets=new ArrayList<>();
        tweets.addAll(Tweet.fetchAllHome());

        tweetAdapter = new TweetAdapter(getContext(), tweets);
        inCall=false;

        populateHomeTimeline(0);
        rvTweets.setAdapter(tweetAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

                populateHomeTimeline(page);
            }
        });

        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateHomeTimeline(0);
            }
        });
        return view;
    }

    private void populateHomeTimeline(final int page) {
        if (!inCall) {
            if (!TimelinesActivity.isOnline()) {
                Toast.makeText(getContext()
                        , "Network unavailable. Try again later.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            inCall=true;
            client.getHomeTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    // Response is automatically parsed into a JSONArray
                    // json.getJSONObject(0).getLong("id");
                    Log.i("DEBUG", "timeline: " + response.toString());
                    if (page == 0) {
                        tweets.clear();
                    }
                    System.out.println("loading more results" + page);
                    tweets.addAll(Tweet.fromJson(response));
                    tweetAdapter.notifyDataSetChanged();
                    inCall = false;
                    mSwipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    System.out.println("error:--------- " + errorResponse.toString());
                    Log.e("DEBUG", "error:--------- " + errorResponse.toString());
                    Toast.makeText(getContext(), "GET TIMELINE FAILED", Toast.LENGTH_LONG).show();
                    try {
                        Toast.makeText(getContext(),
                                errorResponse.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    inCall = false;
                }

                @Override
                public void onUserException(Throwable error) {
                    android.util.Log.d("debug", "eror" + error.getLocalizedMessage());
                    error.printStackTrace();
                    inCall = false;
                }
            }, page);
        }
    }

    public void addNewTweetToList(Tweet tweet){
        tweets.add(0,tweet);
        tweetAdapter.notifyDataSetChanged();
        rvTweets.postDelayed(new Runnable() {
            @Override
            public void run() {
                rvTweets.scrollToPosition(0);
            }
        }, 1000);
    }


}
