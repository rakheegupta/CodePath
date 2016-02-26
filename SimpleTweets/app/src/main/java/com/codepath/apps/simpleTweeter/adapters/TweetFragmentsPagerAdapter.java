package com.codepath.apps.simpleTweeter.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.simpleTweeter.Activities.TimelinesActivity;
import com.codepath.apps.simpleTweeter.Fragments.HomeFragment;
import com.codepath.apps.simpleTweeter.Fragments.MentionsFragment;
import com.codepath.apps.simpleTweeter.Fragments.TimelineFragment;
import com.codepath.apps.simpleTweeter.TweeterClient;
import com.codepath.apps.simpleTweeter.TwitterApplication;
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
public class TweetFragmentsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{TIMELINE_HOME, TIMELINE_MENTIONS};

    public final static String TIMELINE_HOME = "@home";
    public final static String TIMELINE_MENTIONS = "@mentions";


    Context mContext;
    public TweetFragmentsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext=context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return ((TimelinesActivity) mContext).getFragment(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
/*
    private ArrayList<Tweet> populateHomeTimeline(final int page) {
        if (!inHomeCall) {
            if (!TimelinesActivity.isOnline()) {
                Toast.makeText(mContext
                        , "Network unavailable. Try again later.",
                        Toast.LENGTH_LONG).show();
                return null;
            }

            inHomeCall = true;
            JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
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
                    inHomeCall = false;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    System.out.println("error:--------- " + errorResponse.toString());
                    Log.e("DEBUG", "error:--------- " + errorResponse.toString());
                    Toast.makeText(mContext, "GET TIMELINE FAILED", Toast.LENGTH_LONG).show();
                    try {
                        Toast.makeText(mContext,
                                errorResponse.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    inHomeCall = false;
                }

                @Override
                public void onUserException(Throwable error) {
                    android.util.Log.d("debug", "eror" + error.getLocalizedMessage());
                    error.printStackTrace();
                    inHomeCall = false;
                }
            };
            client.getHomeTimeline(handler, page);
        }
    }

    private ArrayList<Tweet> populateMentionsTimeline(final int page) {
        if (!inMentionsCall) {
            if (!TimelinesActivity.isOnline()) {
                Toast.makeText(mContext
                        , "Network unavailable. Try again later.",
                        Toast.LENGTH_LONG).show();
                return null;
            }

            inMentionsCall = true;
            JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
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
                    inMentionsCall = false;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    System.out.println("error:--------- " + errorResponse.toString());
                    Log.e("DEBUG", "error:--------- " + errorResponse.toString());
                    Toast.makeText(mContext, "GET TIMELINE FAILED", Toast.LENGTH_LONG).show();
                    try {
                        Toast.makeText(mContext,
                                errorResponse.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    inMentionsCall = false;
                }

                @Override
                public void onUserException(Throwable error) {
                    android.util.Log.d("debug", "eror" + error.getLocalizedMessage());
                    error.printStackTrace();
                    inMentionsCall = false;
                }
            };
            client.getMentionsTimeline(handler, page);
        }
    }*/
}
