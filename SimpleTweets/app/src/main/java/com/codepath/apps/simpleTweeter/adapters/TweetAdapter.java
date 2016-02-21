package com.codepath.apps.simpleTweeter.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;


import org.w3c.dom.Text;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

        Glide.with(getContext())
                .load(tweet.getProfilePicUrl())
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 4, 1, RoundedCornersTransformation.CornerType.ALL))
                .into(ivPhoto);
        //Picasso.with(getContext()).load(tweet.getProfilePicUrl()).into(ivPhoto);

        //set user name
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        tvUserName.setText(tweet.getUser().getName());

        //screenname
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        tvScreenName.setText("@"+tweet.getUser().getScreenName());
        //set timestamp
        TextView tvTimeStamp =(TextView) convertView.findViewById(R.id.tvTiemStamp);
        tvTimeStamp.setText(tweet.getTimestamp());

        //set text
        TextView tvText= (TextView) convertView.findViewById(R.id.tvText);
        tvText.setText(tweet.getText());


        //photo

        ImageView ivMediaPhoto = (ImageView) convertView.findViewById(R.id.ivMedia);
        if(tweet.getPhoto_url()!=null) {
            ivMediaPhoto.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(tweet.getPhoto_url()).into(ivMediaPhoto);
        }else
        ivMediaPhoto.setVisibility(View.GONE);
        return convertView;
    }
}
