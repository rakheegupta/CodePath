package com.codepath.apps.simpleTweeter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by rakhe on 2/18/2016.
 */
@Parcel(analyze={User.class})
@Table(name = "Users")
public class User extends Model {

    public long getId1() {
        return id;
    }

    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long id;
    @Column(name = "dummy_id")
    public long dummy_id;
    @Column(name = "Name")
    String name;
    @Column(name = "screen_name")
    String screen_name;
    @Column(name = "profile_image_url")
    String profile_image_url;
    @Column(name = "profile_banner_url")
    String profile_banner_url;
    @Column(name = "profile_background_color")
    String profile_background_color;
    @Column(name = "description")
    String description;
    @Column(name = "favourites_count")
    String favourites_count;
    @Column(name = "followers_count")
    String followers_count;
    @Column(name = "friends_count")
    String friends_count;


    public String getDescription() {
        return description;
    }

    public String getFavourites_count() {
        return favourites_count;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public String getFriends_count() {
        return friends_count;
    }

    public String getProfile_background_color() {
        return profile_background_color;
    }

    public String getProfile_banner_url() {
        return profile_banner_url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getScreen_name() {
        return screen_name;
    }


    public User() {
    }

    public User(JSONObject object) {
        super();

        try {
            this.id =object.getLong("id");
            this.name = object.getString("name");
            this.profile_image_url =object.getString("profile_image_url");
            this.screen_name =object.getString("screen_name");
            this.profile_banner_url=object.has("profile_banner_url")?object.getString("profile_banner_url"):"";
            this.description=object.getString("description");
            this.followers_count=object.getString("followers_count");
            this.friends_count=object.getString("friends_count");
            this.favourites_count=object.getString("favourites_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> fromJson(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<User>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject userJson = null;
            try {
                userJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            User user = new User(userJson);
            user.save();
            users.add(user);
        }

        return users;
    }


    public String getName() {
        return name;
    }

    public static User getCurrentUser() {
        return new Select().from(User.class).where("dummy_id = 0").executeSingle();
    }
}
