package com.codepath.apps.simpleTweeter.models;

import android.text.format.DateUtils;
import android.text.format.Time;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rakhe on 2/17/2016.
 */
@Parcel(analyze={Tweet.class})
@Table(name = "Tweets")
public class Tweet extends Model {
    // Define database columns and associated fields
    @Column(name = "tweetId")
    String tweetId;

    @Column(name = "user")
    User user;

    @Column(name = "timestamp")
    String timestamp;

    @Column(name= "photo_url")
    String photo_url;


    @Column(name = "text")
    String text;

    @Column(name= "ppUrl")
    String profilePicUrl;


    public void setText(String text) {
        this.text = text;
    }
    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }


    public User getUser() {
        return user;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        Date tweetDate=null;
       try{
           tweetDate=new Date(timestamp);
       }catch (Exception e){
           e.printStackTrace();
       }
        long time=tweetDate.getTime();
        String tweetDuration[] = DateUtils.getRelativeTimeSpanString(time, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString().split(" ");
        String qualifier = "";
       try {
           if (tweetDuration[1].toLowerCase().contains("minute"))
               qualifier = "m";

           if (tweetDuration[1].toLowerCase().contains("hour"))
               qualifier = "h";
           if (tweetDuration[1].toLowerCase().contains("second"))
               qualifier = "s";
       } catch(ArrayIndexOutOfBoundsException e) {
          System.out.println( e.getLocalizedMessage());
       }
       return tweetDuration[0] + qualifier;
    }

    public String getTweetId() {
        return tweetId;
    }




    // Make sure to always define this constructor with no arguments
    public Tweet() {
        super();
    }
    public Tweet(JSONObject object){
        super();

        try {
            this.tweetId = object.getString("id_str");
            this.timestamp = object.getString("created_at");
            this.text = object.getString("text");
            this.profilePicUrl=object.getJSONObject("user").getString("profile_image_url");
            user = new User(object.getJSONObject("user"));
            this.photo_url = null;
            JSONObject entities = object.getJSONObject("entities");
            if (entities.has("media")) {
                JSONArray mediaArray = entities.getJSONArray("media");
                if (mediaArray.length() > 0) {
                    JSONObject media = mediaArray.getJSONObject(0);
                    if (media.getString("type").equals("photo")) {
                        this.photo_url = mediaArray.getJSONObject(0).getString("media_url");
                    }else if (media.getString("type").equals("video")) {

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = new Tweet(tweetJson);
            //tweet.save();
            tweets.add(tweet);
        }

        return tweets;
    }
}
