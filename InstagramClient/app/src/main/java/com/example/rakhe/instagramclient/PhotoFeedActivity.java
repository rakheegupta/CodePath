package com.example.rakhe.instagramclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotoFeedActivity extends AppCompatActivity {

    public static final String CLIENT_ID="e05c462ebd86446ea48a5af73769b602";
    private ArrayList<Photo> mPhotoFeed;
    private PhotoAdapter mPhotoAdapter;
    ListView lvPhotoFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_feed);
        mPhotoFeed=new ArrayList<Photo>();
        mPhotoAdapter=new PhotoAdapter(this, mPhotoFeed);
        lvPhotoFeed = (ListView)findViewById(R.id.lvPhotoFeed);
        lvPhotoFeed.setAdapter(mPhotoAdapter);
        getPhotoFeed();
    }
    public  ArrayList<Photo> getPhotoFeed()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        String popularPhotoUrl= "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
        client.get(popularPhotoUrl,null,new JsonHttpResponseHandler(){
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
                try{
                    JSONArray photosJson= response.getJSONArray("data");


                    for (int i=0;i<photosJson.length();i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        Photo photo= new Photo();
                        photo.setUserName(photoJson.getJSONObject("user").getString("username"));
                        photo.setUserPhotoUrl(photoJson.getJSONObject("user").getString("profile_picture"));
                        if (photoJson.optJSONObject("caption") != null)
                            photo.setCaptionText(photoJson.getJSONObject("caption").getString("text"));
                        photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));
                        photo.setCreatedTimeString(DateUtils.getRelativeTimeSpanString(photoJson.getLong("created_time") * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString());
                        photo.setUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        mPhotoFeed.add(photo);
                    }
                    mPhotoAdapter.notifyDataSetChanged();
                }catch(JSONException e)
                {
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
}
