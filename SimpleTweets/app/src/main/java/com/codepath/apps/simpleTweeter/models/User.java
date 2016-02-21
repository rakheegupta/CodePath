package com.codepath.apps.simpleTweeter.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by rakhe on 2/18/2016.
 */
@Parcel
public class User {

     String name;

     String screenName;
    String profilePicUrl;

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getScreenName() {
        return screenName;
    }


    public User() {
    }

    public User(JSONObject object) {
        super();

        try {
            name = object.getString("name");
            profilePicUrl=object.getString("profile_image_url");
            screenName=object.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
