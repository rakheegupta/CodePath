package com.example.rakhe.instagramclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakhe on 2/2/2016.
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {

    Context mActivityContext;

    public PhotoAdapter(Context context, List<Photo> objects) {

        super(context, R.layout.list_item, objects);
        mActivityContext=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        final Photo photo=getItem(position);

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
        ExpandableTextView tvCaption = (ExpandableTextView)convertView.findViewById(R.id.tvCaption);
        tvCaption.setText(photo.getCaptionText());


        //last 2 comments
        HtmlTextView tvLastTwoComments = (HtmlTextView) convertView.findViewById(R.id.tvLastTwoComments);
        Comment comment1=photo.getAllComments().get(0);
        Comment comment2=photo.getAllComments().get(1);
        tvLastTwoComments.setText(formatComments(comment1,comment2),TextView.BufferType.EDITABLE);


        //comments count
        TextView tvCommentsCount = (TextView) convertView.findViewById(R.id.tvAllComments);
        tvCommentsCount.setText("View all " + photo.getCommentsCount() + " comments");

        tvCommentsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(getContext(), CommentFeedActivity.class);
                    // put "extras" into the bundle for access in the second activity
                    i.putExtra("photo",photo);
                    // brings up the second activity
                    getContext().startActivity(i);
                }
            }
        );

        return convertView;
    }

    public SpannableStringBuilder formatComments(Comment comment1,Comment comment2){
        SpannableStringBuilder ssb = formatUserName(comment1.getCommentFrom().getmUserName());
        ssb.append(" ");
        ssb.append(comment1.getText());
        ssb.append("\n");
        ssb.append(formatUserName(comment2.getCommentFrom().getmUserName()));
        ssb.append(" ");
        ssb.append(comment2.getText());
        return ssb;
    }

    public SpannableStringBuilder formatUserName(String username){
        ForegroundColorSpan primaryForegroundColorSpan = new ForegroundColorSpan(
                mActivityContext.getResources().getColor(R.color.colorPrimary));
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        SpannableStringBuilder ssb = new SpannableStringBuilder(username);
        // Apply the color span
        ssb.setSpan(
                primaryForegroundColorSpan,            // the span to add
                0,                                 // the start of the span (inclusive)
                ssb.length(),                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder
        // SPAN_EXCLUSIVE_EXCLUSIVE means to not extend the span when additional
        // text is added in later
        ssb.setSpan(boldSpan,0,ssb.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
