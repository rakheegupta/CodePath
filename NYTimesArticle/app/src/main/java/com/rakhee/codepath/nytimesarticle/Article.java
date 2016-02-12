package com.rakhee.codepath.nytimesarticle;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by varungupta on 2/9/16.
 */
public class Article {

    private String title; // headline
    private String url;
    private String thumbnailURL;

    public Article(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static List<Article> createArticleList(int size){
        List<Article> articles = new ArrayList<Article>();

        for (int i = 1; i <= size; i++) {
            articles.add(new Article("the header text","https://upload.wikimedia.org/wikipedia/commons/4/4f/Matterhorn_Riffelsee_2005-06-11.jpg"));
        }

        return articles;
    }

    public Article(JSONObject jsonObject) {
        try {
            url = jsonObject.getString("web_url");
            title = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() == 1) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
            } else if (multimedia.length() > 1) {
                JSONObject multimediaJson = multimedia.getJSONObject(new Random().nextInt(multimedia.length() - 1));
                thumbnailURL = "http://www.nytimes.com/" + multimediaJson.getString("url");
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
