package com.example.rakhe.instagramclient;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotoFeedActivity extends AppCompatActivity {

    public static final String CLIENT_ID="e05c462ebd86446ea48a5af73769b602";
    private ArrayList<Photo> mPhotoFeed;
    private PhotoAdapter mPhotoAdapter;
    private SwipeRefreshLayout mSwipeContainer;
    ListView mLvPhotoFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_feed);
        mPhotoFeed=new ArrayList<Photo>();
        mPhotoAdapter=new PhotoAdapter(this, mPhotoFeed);
        mLvPhotoFeed = (ListView)findViewById(R.id.lvPhotoFeed);
        mLvPhotoFeed.setAdapter(mPhotoAdapter);
        getPhotoFeed();

        // Lookup the swipe container view
        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                getPhotoFeed();
            }
        });
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
    public  ArrayList<Photo> getPhotoFeed()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        String popularPhotoUrl= "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
        client.get(popularPhotoUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //json -> response = json object -> data (key)[] ->
                //user -> username
                //user -> profile_picture
                // caption -> text
                //ArrayList<Comment> commentsList; //comments[]
                //likes ->count
                //created_time
                // images -> standard_resolution ->url
                //Log.i("DEBUG",response.toString());
                try {
                    JSONArray photosJson = response.getJSONArray("data");

                    mPhotoAdapter.clear();
                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        Photo photo = new Photo();

                        photo.setUserName(photoJson.getJSONObject("user").getString("username"));

                        photo.setUserPhotoUrl(photoJson.getJSONObject("user").getString("profile_picture"));

                        if (photoJson.optJSONObject("caption") != null)
                            photo.setCaptionText(photoJson.getJSONObject("caption").getString("text"));

                        photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));

                        String photoDuration[] = DateUtils.getRelativeTimeSpanString(photoJson.getLong("created_time") * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString().split(" ");
                        String qualifier = "";
                        if (photoDuration[1].toLowerCase().contains("minute"))
                            qualifier = "m";
                        if (photoDuration[1].toLowerCase().contains("hour"))
                            qualifier = "h";
                        if (photoDuration[1].toLowerCase().contains("second"))
                            qualifier = "s";
                        photo.setCreatedTimeString(photoDuration[0] + qualifier);

                        photo.setUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));

                        //comments
                        photo.setAllComments(parseAllCommentsFromJSON(photoJson.getJSONObject("comments").getJSONArray("data")));
                        photo.setCommentsCount(photo.getAllComments().size());

                        mPhotoFeed.add(photo);
                    }
                    mPhotoAdapter.notifyDataSetChanged();
                    mSwipeContainer.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        return mPhotoFeed;
    }

    public ArrayList<Comment> parseAllCommentsFromJSON(JSONArray commentsJSON) {
        ArrayList<Comment> allComments = new ArrayList<Comment>();
        for (int i = 0; i < commentsJSON.length(); i++) {
            try {
                JSONObject commentJSON = commentsJSON.getJSONObject(i);
                Comment comment = new Comment();

                comment.setCreatedTimeString(DateUtils.getRelativeTimeSpanString(commentJSON.getLong("created_time") * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString());

                User commentFromUser = new User();
                commentFromUser.setmUserName(commentJSON.getJSONObject("from").getString("username"));
                commentFromUser.setmProfilePictureURL(commentJSON.getJSONObject("from").getString("profile_picture"));
                comment.setCommentFrom(commentFromUser);

                comment.setText(commentJSON.getString("text"));
                allComments.add(comment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return allComments;
    }

}