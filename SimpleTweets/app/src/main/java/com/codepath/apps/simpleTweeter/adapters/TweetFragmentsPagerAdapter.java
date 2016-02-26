package com.codepath.apps.simpleTweeter.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.simpleTweeter.Activities.TimelinesActivity;
import com.codepath.apps.simpleTweeter.Activities.UserProfileActivity;
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
    int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{TIMELINE_HOME, TIMELINE_MENTIONS};

    public final static String TIMELINE_HOME = "@home";
    public final static String TIMELINE_MENTIONS = "@mentions";


    Context mContext;
    public TweetFragmentsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext=context;
    }
    public TweetFragmentsPagerAdapter(FragmentManager fm, Context context,int page_count, String[] titles) {
        super(fm);
        mContext=context;
        PAGE_COUNT=page_count;
        tabTitles=titles;
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if ( mContext instanceof TimelinesActivity)
            return ((TimelinesActivity) mContext).getFragment(position);
        else if ( mContext instanceof UserProfileActivity)
            return ((UserProfileActivity) mContext).getFragment(position);
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
