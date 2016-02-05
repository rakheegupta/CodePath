package com.example.rakhe.instagramclient;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by rakhe on 2/2/2016.
 */
public class Photo implements Serializable {
    //json -> response = json object -> data (key)[] ->
    //user -> username
    //user -> profile_picture
    // caption -> text
    //ArrayList<Comment> commentsList; //comments[]
    //likes ->count
    //created_time
    // images -> standard_resolution ->url
    private static final long serialVersionUID = 0L;
    private String userName;
    private String userPhotoUrl;
    private String captionText;

    private int likesCount;
    private String createdTimeString;
    private String url;
    private ArrayList<Comment> allComments;
    private int commentsCount;
    private String type;
    private String videoURL;

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void setAllComments(ArrayList<Comment> allComments) {
        this.allComments = allComments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getCaptionText() {
        return captionText;
    }

    public void setCaptionText(String captionText) {
        this.captionText = captionText;
    }

    public int getLikesCount() {
         return likesCount;
    }

    public String getLikesCountString() {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedLikes = formatter.format(likesCount)+" likes";
        return formattedLikes;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getCreatedTimeString() {
        return createdTimeString;
    }

    public void setCreatedTimeString(String createdTimeString) {
        this.createdTimeString = createdTimeString;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
