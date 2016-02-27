package com.codepath.apps.simpleTweeter.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTweeter.Activities.UserProfileActivity;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.models.User;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by rakhe on 2/27/2016.
 */
public class UserAdapter extends ArrayAdapter<User> {
    private List<User> mUsers;
    Context mContext;

    public class ViewHolder {
        public ImageView ivPhoto;
        public TextView tvUserName;
        public TextView tvScreenName;
        public TextView tvText;


        public ViewHolder(View itemView) {
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivProfilePic);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
        }
    }

    public UserAdapter(Context context, List<User> users) {
        super(context, R.layout.user_item, users);
        mContext=context;
        mUsers=users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item,parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        final User user=getItem(position);
        viewHolder.tvUserName.setText(user.getName());
        viewHolder.tvScreenName.setText("@"+user.getScreen_name());
        viewHolder.tvText.setText(user.getDescription());

        Glide.with(mContext)
                .load(user.getProfile_image_url())
                .bitmapTransform(new RoundedCornersTransformation(mContext, 4, 1, RoundedCornersTransformation.CornerType.ALL))
                .into(viewHolder.ivPhoto);

        viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,UserProfileActivity.class);
                i.putExtra("user", Parcels.wrap(user));
                mContext.startActivity(i);
            }
        });
        return convertView;
    }
}
