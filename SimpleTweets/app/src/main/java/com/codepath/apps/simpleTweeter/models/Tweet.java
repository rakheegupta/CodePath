package com.codepath.apps.simpleTweeter.models;

import android.text.format.DateUtils;
import android.text.format.Time;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rakhe on 2/17/2016.
 */
@Table(name = "Tweets")
public class Tweet extends Model {
    // Define database columns and associated fields
    @Column(name = "tweetId")
    String tweetId;
    @Column(name = "userName")
    String userName;
    @Column(name = "timestamp")
    String timestamp;

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "text")
    String text;

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

    public String getUserName() {
        return userName;
    }

    @Column(name= "ppUrl")
    String profilePicUrl;

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
            this.userName = object.getJSONObject("user").getString("screen_name");
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
