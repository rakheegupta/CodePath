package com.codepath.apps.simpleTweeter.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPhoto;
        public TextView tvUserName;
        public TextView tvScreenName;
        public TextView tvTimeStamp;
        public TextView tvText;
        public ImageView ivMediaPhoto;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPhoto =(ImageView)itemView.findViewById(R.id.ivProfilePic);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            tvTimeStamp =(TextView) itemView.findViewById(R.id.tvTiemStamp);
            tvText= (TextView) itemView.findViewById(R.id.tvText);
            ivMediaPhoto = (ImageView) itemView.findViewById(R.id.ivMedia);
        }
    }

    private List< com.codepath.apps.simpleTweeter.models.Tweet> mTweets;
    Context mContext;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        mTweets = tweets;
    }

    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.tweet_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TweetAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Tweet tweet = mTweets.get(position);

        // Set image

        Glide.with(mContext)
                .load(tweet.getUser().getProfilePicUrl())
                .bitmapTransform(new RoundedCornersTransformation(mContext, 4, 1, RoundedCornersTransformation.CornerType.ALL))
                .into(viewHolder.ivPhoto);
        //Picasso.with(mContext).load(tweet.getProfilePicUrl()).into(ivPhoto);

        //set user name
        viewHolder.tvUserName.setText(tweet.getUser().getName());

        //screenname
        viewHolder.tvScreenName.setText("@"+tweet.getUser().getScreenName());

        //set timestamp
        viewHolder.tvTimeStamp.setText(tweet.getTimestamp());

        //set text
        viewHolder.tvText.setText(tweet.getText());


        //photo

        if(tweet.getPhoto_url()!=null) {
            viewHolder.ivMediaPhoto.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(tweet.getPhoto_url()).into(viewHolder.ivMediaPhoto);
        }else {
            viewHolder.ivMediaPhoto.setVisibility(View.GONE);
        }

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
