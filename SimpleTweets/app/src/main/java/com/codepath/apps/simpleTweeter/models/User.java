package com.codepath.apps.simpleTweeter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by rakhe on 2/18/2016.
 */
@Parcel(analyze={User.class})
@Table(name = "Users")
public class User extends Model {
    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long remoteId;
    @Column(name = "Name")
    String name;
    @Column(name = "ScreenName")
    String screenName;
    @Column(name = "ProfilePicUrl")
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
            this.remoteId=object.getLong("id");
            this.name = object.getString("name");
            this.profilePicUrl=object.getString("profile_image_url");
            this.screenName=object.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public static User getCurrentUser() {
        return new Select().from(User.class).where("remote_id = 0").executeSingle();
    }
}
