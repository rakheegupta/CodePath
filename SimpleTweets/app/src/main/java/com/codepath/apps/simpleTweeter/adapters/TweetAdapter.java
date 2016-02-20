package com.codepath.apps.simpleTweeter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rakhe on 2/18/2016.
 */
public class TweetAdapter extends ArrayAdapter<Tweet> {
    public TweetAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item,parent,false);
        }
        Tweet tweet = getItem(position);
        // Set image
        ImageView ivPhoto =(ImageView)convertView.findViewById(R.id.ivProfilePic);
        Picasso.with(getContext()).load(tweet.getProfilePicUrl()).into(ivPhoto);

        //set user name
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        tvUserName.setText(tweet.getUserName());

        //set timestamp
        TextView tvTimeStamp =(TextView) convertView.findViewById(R.id.tvTiemStamp);
        tvTimeStamp.setText(tweet.getTimestamp());

        //set text
        TextView tvText= (TextView) convertView.findViewById(R.id.tvText);
        tvText.setText(tweet.getText());
        return convertView;
    }
}
