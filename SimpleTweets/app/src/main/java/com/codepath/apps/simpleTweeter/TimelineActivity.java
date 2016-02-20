package com.codepath.apps.simpleTweeter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.simpleTweeter.adapters.TweetAdapter;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.codepath.apps.simpleTweeter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakhe on 2/17/2016.
 */
public class TimelineActivity extends AppCompatActivity {
    TweeterClient client;
    ArrayList<Tweet> tweets;
    TweetAdapter tweetAdapter;
    RequestParams params;
    boolean inCall;
    User user;
    public final static String EXTRA_ADD_TWEET_MESSAGE = "com.codepath.apps.simpleTweeter.TimelineActivity.EXTRA_ADD_TWEET_USER";
    public final static String EXTRA_ADD_TWEET_RESULT = "com.codepath.apps.simpleTweeter.TimelineActivity.EXTRA_ADD_TWEET_RESULT";
    public final static int ADD_MESSAGE_REQUEST_CODE = 37;
    private SwipeRefreshLayout mSwipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets=new ArrayList<>();
        client = TwitterApplication.getRestClient();

        getUser();
        
        tweetAdapter = new TweetAdapter(this, tweets);
        params= new RequestParams();
        params.put("count", 25);
        inCall=false;

        populateTimeline();
        lvTweets.setAdapter(tweetAdapter);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                params.put("page", page);
                populateTimeline();
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline();
            }
        });
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void populateTimeline() {
        if (!inCall) {
            inCall=true;
            client.getHomeTimeline(params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            // Response is automatically parsed into a JSONArray
                            // json.getJSONObject(0).getLong("id");
                            Log.d("DEBUG", "timeline: " + response.toString());
                            tweets.addAll(Tweet.fromJson(response));
                            tweetAdapter.notifyDataSetChanged();
                            inCall=false;
                            mSwipeContainer.setRefreshing(false);
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("DEBUG", "error:--------- " + errorResponse.toString());
                            Toast.makeText(TimelineActivity.this,"GET TIMELINE FAILED",Toast.LENGTH_LONG).show();
                            inCall=false;
                        }

                        @Override
                        public void onUserException(Throwable error) {
                            android.util.Log.d("debug","eror"+error.getLocalizedMessage());
                            error.printStackTrace();
                            inCall=false;
                        }
            });
        }
    }
    private void getUser(){

        client.verifyCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = new User(response);
                getSupportActionBar().setTitle(user.getName());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("debug", "user not found");
                Toast.makeText(TimelineActivity.this,"User not found",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("debug", "user not found");
                Toast.makeText(TimelineActivity.this,"User not found",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", "user not found");
                Toast.makeText(TimelineActivity.this,"User not found",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserException(Throwable error) {
                Log.d("debug", "user not found");
                Toast.makeText(TimelineActivity.this,"User not found",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void composeActivity(MenuItem item) {
        Intent i =new Intent(this,ComposeActivity.class);
        i.putExtra(EXTRA_ADD_TWEET_MESSAGE,user);
        startActivityForResult(i, ADD_MESSAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String newTweetText = data.getStringExtra(EXTRA_ADD_TWEET_RESULT);
            Toast.makeText(this,newTweetText,Toast.LENGTH_SHORT).show();
            client.postTweet(newTweetText,new JsonHttpResponseHandler(){
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("debug", "unable to add tweet");
                    Toast.makeText(TimelineActivity.this,responseString,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("debug", "unable to add tweet");
                    Toast.makeText(TimelineActivity.this,errorResponse.toString(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Tweet newTweet=new Tweet(response);
                    tweets.add(0,newTweet);
                    tweetAdapter.notifyDataSetChanged();
                }

                @Override
                public void onUserException(Throwable error) {
                    super.onUserException(error);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compose,menu);
        MenuItem composeItem =menu.findItem(R.id.miComposeTweet);
        return true;
    }
}
