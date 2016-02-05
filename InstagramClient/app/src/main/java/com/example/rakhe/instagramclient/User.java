package com.example.rakhe.instagramclient;

import java.io.Serializable;

/**
 * Created by rakhe on 2/3/2016.
 */
public class User implements Serializable {
    private String mUserName;
    private String mFullName;
    private String mProfilePictureURL;

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmProfilePictureURL() {
        return mProfilePictureURL;
    }

    public void setmProfilePictureURL(String mProfilePictureURL) {
        this.mProfilePictureURL = mProfilePictureURL;
    }

    public String getmFullName() {
        return mFullName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }
}
