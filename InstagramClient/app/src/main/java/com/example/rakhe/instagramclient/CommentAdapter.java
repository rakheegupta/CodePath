package com.example.rakhe.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakhe on 2/3/2016.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, ArrayList<Comment> objects) {
        super(context, R.layout.activity_comment_feed_activity, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_item,parent,false);
        }
        Comment comment= getItem(position);

        // Set user image
        ImageView ivUserProfilePic = (ImageView) convertView.findViewById(R.id.ivCommentUserProfilePhoto);
        Picasso.with(getContext()).load(comment.getCommentFrom().getmProfilePictureURL()).into(ivUserProfilePic);

        ///set comment text
        TextView tvCommentText = (TextView) convertView.findViewById(R.id.tvCommentText);
        tvCommentText.setText(comment.getText());

        //set duration
        TextView tvCommentDuration = (TextView) convertView.findViewById(R.id.tvCommentDuration);
        tvCommentDuration.setText(comment.getCreatedTimeString());

        return convertView;
    }
}
