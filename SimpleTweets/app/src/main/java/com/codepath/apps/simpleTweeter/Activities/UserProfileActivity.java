package com.codepath.apps.simpleTweeter.Activities;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTweeter.Fragments.EmptyFragment;
import com.codepath.apps.simpleTweeter.Fragments.TimelineFragment;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.TwitterApplication;

import com.codepath.apps.simpleTweeter.adapters.TweetFragmentsPagerAdapter;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.codepath.apps.simpleTweeter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class UserProfileActivity extends AppCompatActivity implements TimelineFragment.OnReplyClickedListener_Activity{
    @Override
    public void OnClick(User user) {
        Toast.makeText(UserProfileActivity.this, "Inside onclick in activity", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,NewTweetActivity.class);
        i.putExtra(TimelinesActivity.EXTRA_ADD_TWEET_MESSAGE, Parcels.wrap(user));
        i.putExtra("retweet",true);
        startActivityForResult(i, TimelinesActivity.ADD_MESSAGE_REQUEST_CODE);
    }
    TimelineFragment homeTimeline;
    Fragment favTimeline;
    User user;


    FragmentPagerAdapter adapterViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (savedInstanceState ==null)
        //getintent
            user =  Parcels.unwrap(getIntent().getParcelableExtra("user"));
        else
        //getfrom saved instance state
            user= Parcels.unwrap(savedInstanceState.getParcelable("user"));

        //handle actionbar
        //getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(user.getName());

        ImageView background = (ImageView) findViewById(R.id.ivHeader);
        if (user.getProfile_banner_url() != null)
            Glide.with(this)
                .load(user.getProfile_banner_url())
                .bitmapTransform(new RoundedCornersTransformation(this, 4, 1, RoundedCornersTransformation.CornerType.ALL))
                .into(background);
        else background.setBackgroundColor(getResources().getColor(R.color.primary_blue));

        TextView Name = (TextView) findViewById(R.id.acTitle);
        TextView handle =(TextView) findViewById(R.id.acHandle);
        TextView desc = (TextView) findViewById(R.id.description);
        TextView following = (TextView) findViewById(R.id.numFollowing);
        final TextView followers = (TextView) findViewById(R.id.numFollowers);

        Name.setText(user.getName());
        handle.setText("@"+user.getScreen_name());
        desc.setText(user.getDescription());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (user.getFollowers_count()!=null)
            followers.setText(formatter.format(Integer.parseInt(user.getFollowers_count())));
        if (user.getFriends_count()!=null)
         following.setText(formatter.format(Integer.parseInt(user.getFriends_count())));

        //onclicks
        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserProfileActivity.this,UsersListActivity.class);
                i.putExtra("user_id",user.getId1());
                i.putExtra("users_type","Followers");
                startActivity(i);
            }
        });
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserProfileActivity.this,UsersListActivity.class);
                i.putExtra("user_id",user.getId1());
                i.putExtra("users_type","Following");
                startActivity(i);
            }
        });

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

    public Fragment getFragment(int position){
        if (position == 0) {
            // Return home timeline fragment
            if (homeTimeline == null){
                homeTimeline = TimelineFragment.newInstance(
                        new TimelineFragment.ITweetsGetter() {
                            @Override
                            public void populateTimeline(int page, AsyncHttpResponseHandler handler) {
                                TwitterApplication.getRestClient().getUserTimeline(user.getId1() ,page, handler);;
                            }
                            public String getType(){
                                return getResources().getString(R.string.USER_TIMELINE);
                            }
                        }
                );
                //TimelineFragment.newInstance(0);

            }

            return homeTimeline;
        }else if (position == 1) {
            // Return fav timeline fragment
            if (favTimeline == null){
                favTimeline = EmptyFragment.newInstance( );
                //TimelineFragment.newInstance(0);

            }

            return favTimeline;
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("user",Parcels.wrap(user));
    }
}
