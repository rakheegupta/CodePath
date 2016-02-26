package com.codepath.apps.simpleTweeter.Activities;

import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTweeter.Fragments.TimelineFragment;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.TwitterApplication;

import com.codepath.apps.simpleTweeter.adapters.TweetFragmentsPagerAdapter;
import com.codepath.apps.simpleTweeter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class UserProfileActivity extends AppCompatActivity {
    TimelineFragment homeTimeline;
    TimelineFragment favTimeline;
    User user;

    FragmentPagerAdapter adapterViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //getintent
        user =  Parcels.unwrap(getIntent().getParcelableExtra("user"));

        //handle actionbar
        //getSupportActionBar().hide();

        ImageView background = (ImageView) findViewById(R.id.ivHeader);
        if (user.getProfile_background_image_url() != null)
            Glide.with(this)
                .load(user.getProfile_background_image_url())
                .bitmapTransform(new RoundedCornersTransformation(this, 4, 1, RoundedCornersTransformation.CornerType.ALL))
                .into(background);
        else if (user.getProfile_background_color() != null)
            background.setBackgroundColor(Integer.parseInt(user.getProfile_background_color()));
        else background.setBackgroundColor(getResources().getColor(R.color.primary_blue));

        TextView Name = (TextView) findViewById(R.id.acTitle);
        TextView handle =(TextView) findViewById(R.id.acHandle);
        TextView desc = (TextView) findViewById(R.id.description);
        TextView following = (TextView) findViewById(R.id.numFollowing);
        TextView followers = (TextView) findViewById(R.id.numFollowers);

        Name.setText(user.getName());
        handle.setText("@"+user.getScreen_name());
        desc.setText(user.getDescription());
        followers.setText(user.getFollowers_count());
        following.setText(user.getFriends_count());

        ImageView UserImage = (ImageView) findViewById(R.id.UserImage);
        Glide.with(this)
                .load(user.getProfile_image_url())
                .bitmapTransform(new RoundedCornersTransformation(this, 4, 1, RoundedCornersTransformation.CornerType.ALL))
                .into(UserImage);

        homeTimeline = null;
        favTimeline = null;
        //setup pager and adapter
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new TweetFragmentsPagerAdapter(getSupportFragmentManager(),this,2,new String[]{"@"+user.getScreen_name(),"Photos"});
        vpPager.setAdapter(adapterViewPager);

        //set up tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

    }

    public TimelineFragment getFragment(int position){
        if (position == 0) {
            // Return home timeline fragment
            if (homeTimeline == null){
                homeTimeline = TimelineFragment.newInstance(
                        new TimelineFragment.ITweetsGetter() {
                            @Override
                            public void populateTimeline(int page, AsyncHttpResponseHandler handler) {
                                TwitterApplication.getRestClient().getUserTimeline(user.getId1() ,page, handler);;
                            }
                        }
                );
                //TimelineFragment.newInstance(0);
                //homeTimeline = HomeFragment.newInstance(0);
            }

            return homeTimeline;
        }else if (position == 1) {
            // Return home timeline fragment
            if (favTimeline == null){
                favTimeline = TimelineFragment.newInstance(
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

            return favTimeline;
        }
        return null;
    }
}
