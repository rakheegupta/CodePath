package com.codepath.apps.simpleTweeter.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.codepath.apps.simpleTweeter.AdapterCallBack;
import com.codepath.apps.simpleTweeter.EndlessRecyclerOnScrollListener;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.TweeterClient;
import com.codepath.apps.simpleTweeter.TwitterApplication;
import com.codepath.apps.simpleTweeter.adapters.TweetAdapter;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.codepath.apps.simpleTweeter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakhe on 2/23/2016.
 */

public class TimelineFragment extends Fragment implements AdapterCallBack {
    @Override
    public void onReplyClicked(User user) {

        //Toast.makeText(getContext(), "Inside onclick in fragment", Toast.LENGTH_SHORT).show();
        listenerForActivity.OnClick(user);
    }

    public interface OnReplyClickedListener_Activity {
        void OnClick(User user);
    }
    OnReplyClickedListener_Activity listenerForActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listenerForActivity = (OnReplyClickedListener_Activity) activity;
    }

    // Interface to get tweets
    public interface ITweetsGetter{
        void populateTimeline(int page, AsyncHttpResponseHandler handler);
        String getType();
    }


    public ITweetsGetter tweetsGetter;

    public static TimelineFragment newInstance(ITweetsGetter tweetsGetter) {
        TimelineFragment fragment = new TimelineFragment();
        fragment.tweetsGetter=tweetsGetter;
        return fragment;
    }

    ArrayList<Tweet> tweets;
    TweetAdapter tweetAdapter;
    ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up adapter
        tweets=new ArrayList<>();
        //default -- fetch page 0 tweets
        tweets.addAll(Tweet.fetchAllHome(getContext())); //get all cached tweets
        tweetAdapter = new TweetAdapter(getContext(), tweets,this);
    }

    TweeterClient client;
    RecyclerView rvTweets;

    private SwipeRefreshLayout mSwipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        // Setup handles to view objects here
        //set up RecyclerView
        rvTweets = (RecyclerView) view.findViewById(R.id.rvTweets);
        rvTweets.setAdapter(tweetAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(layoutManager);

        //fetch tweets
        populateTimeline(0);

        //add on scroll listener
        rvTweets.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                populateTimeline(page);
            }
        });

        //set up on swipe refresh
        mSwipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline(0);
            }
        });
        return view;
    }

    boolean inCall;
    private void populateTimeline(final int page) {
        inCall = false;
        if (!TimelinesActivity.isOnline()) {
            Toast.makeText(getContext()
                    , "Network unavailable. Try again later.",
                    Toast.LENGTH_LONG).show();
            if (tweetsGetter != null){
                switch( tweetsGetter.getType() ){
                    //case getResources().getString(R.string.HOME_TIMELINE):tweets.addAll(Tweet.fetchAllHome(getContext()));
                }
            }

            return;
        }
        // Create a progressbar
        pDialog = new ProgressDialog(getContext());
        // Set progressbar title
        pDialog.setTitle("Fetching Tweets");
        // Set progressbar message
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();
        tweetsGetter.populateTimeline(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i("DEBUG", "timeline: " + response.toString());
                if (page == 0) {
                    tweets.clear();
                }
                System.out.println("loading more results" + page);
                tweets.addAll(Tweet.fromJson(response, tweetsGetter.getType()));
                int curSize = tweetAdapter.getItemCount();
                //tweetAdapter.notifyItemRangeInserted(curSize, tweets.size() - 1);
                tweetAdapter.notifyDataSetChanged();
                // Try to get more tweets only if we got something in this try
                // This is to ensure no infinite loop
                inCall = (tweets.size() > 0);
                mSwipeContainer.setRefreshing(false);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                android.util.Log.e("tweets error", responseString);
                Toast.makeText(getActivity(), "Failed to get tweets", Toast.LENGTH_SHORT).show();
                inCall = false;
                mSwipeContainer.setRefreshing(false);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                android.util.Log.e("tweets error", errorResponse.toString());
                Toast.makeText(getActivity(), "Failed to get tweets", Toast.LENGTH_SHORT).show();
                inCall = false;
                mSwipeContainer.setRefreshing(false);
                pDialog.dismiss();
            }
        });
    }
    //result after profile activity has finished
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
