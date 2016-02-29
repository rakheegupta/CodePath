package com.codepath.apps.simpleTweeter.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTweeter.Activities.NewTweetActivity;
import com.codepath.apps.simpleTweeter.Activities.TimelinesActivity;
import com.codepath.apps.simpleTweeter.Activities.UserProfileActivity;
import com.codepath.apps.simpleTweeter.AdapterCallBack;
import com.codepath.apps.simpleTweeter.Fragments.TimelineFragment;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.TwitterApplication;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.apache.http.Header;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by rakhe on 2/18/2016.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    AdapterCallBack onReplyClickedListener;

    public interface TweetsAdapterListener {
        void onReplyClicked(String usersInfo, long in_reply_to_status_id);
    }

    TweetsAdapterListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPhoto;
        public TextView tvUserName;
        public TextView tvScreenName;
        public TextView tvTimeStamp;
        public TextView tvText;
        public ImageView ivMediaPhoto;
        public ImageView ivReply;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPhoto =(ImageView)itemView.findViewById(R.id.ivProfilePic);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            tvTimeStamp =(TextView) itemView.findViewById(R.id.tvTiemStamp);
            tvText= (TextView) itemView.findViewById(R.id.tvText);
            ivMediaPhoto = (ImageView) itemView.findViewById(R.id.ivMedia);
            ivReply = (ImageView) itemView.findViewById(R.id.ivReply);
        }
    }

    private List< com.codepath.apps.simpleTweeter.models.Tweet> mTweets;
    Context mContext;

    public TweetAdapter(Context context, List<Tweet> tweets, AdapterCallBack listener) {
        mContext = context;
        mTweets = tweets;
        onReplyClickedListener = listener;
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
        final Tweet tweet = mTweets.get(position);

        // Set image

        Glide.with(mContext)
                .load(tweet.getUser().getProfile_image_url())
                .bitmapTransform(new RoundedCornersTransformation(mContext, 4, 1, RoundedCornersTransformation.CornerType.ALL))
                .into(viewHolder.ivPhoto);

        viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,UserProfileActivity.class);
                i.putExtra("user", Parcels.wrap(tweet.getUser()));
                mContext.startActivity(i);
            }
        });
        //Picasso.with(mContext).load(tweet.getProfile_image_url()).into(ivPhoto);

        //set user name
        viewHolder.tvUserName.setText(tweet.getUser().getName());

        //screenname
        viewHolder.tvScreenName.setText("@"+tweet.getUser().getScreen_name());

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

        //reply
        viewHolder.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "Inside onclick in adapter", Toast.LENGTH_SHORT).show();
                onReplyClickedListener.onReplyClicked(tweet.getUser());
            }
        });
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
