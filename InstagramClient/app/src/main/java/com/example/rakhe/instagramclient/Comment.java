package com.example.rakhe.instagramclient;
import java.io.Serializable;

/**
 * Created by rakhe on 2/3/2016.
 */
public class Comment implements Serializable {
    private String createdTimeString;
    private String text;
    private User commentFrom;

    public String getCreatedTimeString() {
        return createdTimeString;
    }

    public void setCreatedTimeString(String createdTimeString) {
        this.createdTimeString = createdTimeString;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getCommentFrom() {
        return commentFrom;
    }

    public void setCommentFrom(User commentFrom) {
        this.commentFrom = commentFrom;
    }
}
