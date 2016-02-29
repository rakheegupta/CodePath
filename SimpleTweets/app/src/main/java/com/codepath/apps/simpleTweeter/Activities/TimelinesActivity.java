package com.codepath.apps.simpleTweeter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTweeter.AdapterCallBack;
import com.codepath.apps.simpleTweeter.Fragments.TimelineFragment;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.TwitterApplication;
import com.codepath.apps.simpleTweeter.adapters.TweetFragmentsPagerAdapter;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.codepath.apps.simpleTweeter.models.User;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by rakhe on 2/17/2016.
 */
public class TimelinesActivity extends AppCompatActivity implements TimelineFragment.OnReplyClickedListener_Activity {
    @Override
    public void OnClick(User user) {
        //Toast.makeText(TimelinesActivity.this, "Inside onclick in activity", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,NewTweetActivity.class);
        i.putExtra(EXTRA_ADD_TWEET_MESSAGE, Parcels.wrap(user));
        i.putExtra("retweet",true);


        startActivityForResult(i, ADD_MESSAGE_REQUEST_CODE);
    }

    User user;

    TimelineFragment homeTimeline;
    TimelineFragment mentionsTimeline;
    TimelineFragment searchResultsTimeline;

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
            setUpActionBar();
        }
        getUser();

        homeTimeline = null;
        mentionsTimeline = null;
        searchResultsTimeline=null;

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
                Intent i = new Intent(TimelinesActivity.this, NewTweetActivity.class);
                i.putExtra(EXTRA_ADD_TWEET_MESSAGE, Parcels.wrap(user));
                i.putExtra("retweet",false);
                startActivityForResult(i, ADD_MESSAGE_REQUEST_CODE);
            }
        });


    }


    private void setUpActionBar(){
        //setup actionbar for this activity
        if (user != null) {
            getSupportActionBar().setCustomView(R.layout.user_photo);

            ImageView userProfilePhoto = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.acProfilePic);
            Glide.with(this)
                    .load(user.getProfile_image_url())
                    .bitmapTransform(new RoundedCornersTransformation(this, 4, 1, RoundedCornersTransformation.CornerType.ALL))
                    .into(userProfilePhoto);
            userProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(TimelinesActivity.this,UserProfileActivity.class);
                    i.putExtra("user",Parcels.wrap(user));
                    startActivity(i);
                }
            });

            TextView userName =(TextView) getSupportActionBar().getCustomView().findViewById(R.id.acTitle);
            userName.setText(user.getName());
            TextView userHandle =(TextView) getSupportActionBar().getCustomView().findViewById(R.id.acHandle);
            userHandle.setText("@"+ user.getScreen_name());
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                    | ActionBar.DISPLAY_SHOW_HOME);

            EditText etsearch = (EditText) findViewById(R.id.searchVal);
        }
    }

    private void getUser(){
        if (!isOnline()) {
            Toast.makeText(TimelinesActivity.this, "Network unavailable. Try again later.", Toast.LENGTH_LONG).show();
            return;
        }
        TwitterApplication.getRestClient().verifyCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               // user =  new Gson().fromJson(response.toString(), User.class);
                user=new User(response);
                user.dummy_id = 0;
                user.save();
                setUpActionBar();
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
                    //Toast.makeText(TimelinesActivity.this, "Successfully replied", Toast.LENGTH_SHORT).show();
                    Tweet newTweet = new Tweet(response,getResources().getString( R.string.HOME_TIMELINE));
                    newTweet.save();
                    Tweet newTweet1 = new Tweet(response,getResources().getString(R.string.USER_TIMELINE));
                    newTweet1.save();
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
                            public String getType(){
                                return getResources().getString(R.string.HOME_TIMELINE);
                            }
                        }
                );
                //TimelineFragment.newInstance(0);

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
                            public String getType(){
                                return getResources().getString(R.string.MENTIONS_TIMELINE);
                            }
                        }
                );
                //mentionsTimeline = TimelineFragment.newInstance(0);
            }
            return mentionsTimeline;
        }

        return null;
    }

    public void onSearch(View view) {
        EditText etsearch = (EditText) findViewById(R.id.searchVal);
        etsearch.setVisibility(View.VISIBLE);
        etsearch.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
