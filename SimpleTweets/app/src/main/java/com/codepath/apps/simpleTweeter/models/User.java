package com.codepath.apps.simpleTweeter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rakhe on 2/18/2016.
 */

public class User implements Serializable {

    private String name;
    private String UserId;
    private String screenName;

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    private String profilePicUrl;

    public User(JSONObject object) {
        super();

        try {
            name = object.getString("name");
            profilePicUrl=object.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
