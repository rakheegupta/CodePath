package com.codepath.apps.simpleTweeter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.simpleTweeter.Fragments.HomeFragment;
import com.codepath.apps.simpleTweeter.Fragments.TimelineFragment;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.TweeterClient;
import com.codepath.apps.simpleTweeter.TwitterApplication;
import com.codepath.apps.simpleTweeter.adapters.TweetFragmentsPagerAdapter;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.codepath.apps.simpleTweeter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;

/**
 * Created by rakhe on 2/17/2016.
 */
public class TimelinesActivity extends AppCompatActivity {

    User user;

    TimelineFragment homeTimeline;
    TimelineFragment mentionsTimeline;

    public final static String EXTRA_ADD_TWEET_MESSAGE = "com.codepath.apps.simpleTweeter.Activities.TimelinesActivity.EXTRA_ADD_TWEET_USER";
    public final static String EXTRA_ADD_TWEET_RESULT = "com.codepath.apps.simpleTweeter.Activities.TimelinesActivity.EXTRA_ADD_TWEET_RESULT";
    public final static int ADD_MESSAGE_REQUEST_CODE = 37;

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //set up the user
        user = User.getCurrentUser();
        if (user != null) {
            getSupportActionBar().setTitle(user.getName());
        }
        getUser();

        homeTimeline = null;
        mentionsTimeline = null;

        //setup pager and adapter
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new TweetFragmentsPagerAdapter(getSupportFragmentManager(),this);
        vpPager.setAdapter(adapterViewPager);

        //set up tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        //set up profile button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCompose);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TimelinesActivity.this, NewTweetActivity.class);
                i.putExtra(EXTRA_ADD_TWEET_MESSAGE, Parcels.wrap(user));
                startActivityForResult(i, ADD_MESSAGE_REQUEST_CODE);
            }
        });
    }

    private void getUser(){
        if (!isOnline()) {
            Toast.makeText(TimelinesActivity.this, "Network unavailable. Try again later.", Toast.LENGTH_LONG).show();
            return;
        }
        TwitterApplication.getRestClient().verifyCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = new User(response);
                user.remoteId = 0;
                user.save();
                getSupportActionBar().setTitle(user.getName());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("debug", "user not found");
                Toast.makeText(TimelinesActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("debug", "user not found");
                // Toast.makeText(TimelinesActivity.this,"User not found",Toast.LENGTH_SHORT).show();
                try {
                    Toast.makeText(TimelinesActivity.this, errorResponse.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUserException(Throwable error) {
                Log.d("debug", "user not found");
                Toast.makeText(TimelinesActivity.this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String newTweetText = data.getStringExtra(EXTRA_ADD_TWEET_RESULT);
           // Toast.makeText(this,newTweetText,Toast.LENGTH_SHORT).show();
            TwitterApplication.getRestClient().postTweet(newTweetText, new JsonHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("debug", "unable to add tweet");
                    Toast.makeText(TimelinesActivity.this, responseString, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("debug", "unable to add tweet");
                    try {
                        if (errorResponse != null)
                            Toast.makeText(TimelinesActivity.this, errorResponse.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //talk to timelineFragment
                    //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    Tweet newTweet = new Tweet(response);
                    homeTimeline.addNewTweetToList(newTweet);
                    // ft.replace(R.id.your_placeholder, homeTimeline);
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    // ft.commit();

                }

                @Override
                public void onUserException(Throwable error) {
                    super.onUserException(error);
                }
            });
        }
    }
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile, menu);
        return true;
    }

    public TimelineFragment getFragment(int position){
        if (position == 0) {
            // Return home timeline fragment
            if (homeTimeline == null){
                homeTimeline = TimelineFragment.newInstance(
                        new TimelineFragment.ITweetsGetter() {
                            @Override
                            public void populateTimeline(int page, AsyncHttpResponseHandler handler) {
                                TwitterApplication.getRestClient().getHomeTimeline(handler,page);
                            }
                        }
                );
                //TimelineFragment.newInstance(0);
                //homeTimeline = HomeFragment.newInstance(0);
            }

            return homeTimeline;
        } else if (position == 1){
            if (mentionsTimeline == null) {
                mentionsTimeline  = TimelineFragment.newInstance(
                        new TimelineFragment.ITweetsGetter() {
                            @Override
                            public void populateTimeline(int page, AsyncHttpResponseHandler handler) {
                                TwitterApplication.getRestClient().getMentionsTimeline(handler,page);
                            }
                        }
                );
                //mentionsTimeline = TimelineFragment.newInstance(0);
            }
            return mentionsTimeline;
        }

        return null;
    }
}
