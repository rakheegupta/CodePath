package com.example.rakhe.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rakhe on 2/2/2016.
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {


    public PhotoAdapter(Context context, List<Photo> objects) {
        super(context, R.layout.list_item, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Photo photo=getItem(position);

        // Set user image
        ImageView ivUserProfilePic = (ImageView) convertView.findViewById(R.id.ivUserProfilePhoto);
        Picasso.with(getContext()).load(photo.getUserPhotoUrl()).into(ivUserProfilePic);

        // Set user name
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        tvUserName.setText(photo.getUserName());

        // Set image
        ImageView ivPhoto =(ImageView)convertView.findViewById(R.id.ivPhoto);
        Picasso.with(getContext()).load(photo.getUrl()).into(ivPhoto);

        //set duration
        TextView tvDuration =(TextView) convertView.findViewById(R.id.tvDuration);
        tvDuration.setText(photo.getCreatedTimeString());

        //set likescount
        TextView tvLikesCount = (TextView)convertView.findViewById(R.id.tvLikesCount);
        tvLikesCount.setText(photo.getLikesCountString());

        //set caption
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        tvCaption.setText(photo.getCaptionText());

        return convertView;
    }
}
