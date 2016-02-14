package com.rakhee.codepath.nytimesarticle.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by varungupta on 2/9/16.
 */
@Parcel
public class Article {

    public String title; // headline
    public String url;
    public String thumbnailURL;
    public int width;
    public int height;

    public Article() {

    }

    public Article(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Article(JSONObject jsonObject) {
        try {
            url = jsonObject.getString("web_url");
            title = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() >= 1) {
                JSONObject multimediaJson = multimedia.getJSONObject(new Random().nextInt(multimedia.length()));
                thumbnailURL = "http://www.nytimes.com/" + multimediaJson.getString("url");
                width = multimediaJson.getInt("width");
                height = multimediaJson.getInt("height");
            } else {
                thumbnailURL = "";
            }
        } catch (JSONException e) {
            Log.d("here", e.toString());
        }
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array) {
        ArrayList<Article> results = new ArrayList<Article>();
        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Article(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

}
