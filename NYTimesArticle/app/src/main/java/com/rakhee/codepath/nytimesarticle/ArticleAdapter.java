package com.rakhee.codepath.nytimesarticle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.List;

/**
 * Created by rakhe on 2/11/2016.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    Context context;
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public DynamicHeightImageView ivThumbnail;
        public CardView cardView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivThumbnail = (DynamicHeightImageView) itemView.findViewById(R.id.ivThumbnail);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    // Store a member variable for the contacts
    private List<Article> mArticles;

    // Pass in the contact array into the constructor
    public ArticleAdapter(List<Article> articles) {
        mArticles = articles;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_article, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Article article = mArticles.get(position);

        // Set item views based on the data model
        TextView tvTitle = viewHolder.tvTitle;
        tvTitle.setText(article.getTitle());

        DynamicHeightImageView ivThumbnail = viewHolder.ivThumbnail;
        String imageUrl = article.getThumbnailURL();
        if (!IsNullOrEmpty(imageUrl)) {
            ivThumbnail.setHeightRatio(((double) article.getHeight()) / article.getWidth());
            Glide.with(context).load(imageUrl).into(ivThumbnail);
        }
        else {
            ivThumbnail.setHeightRatio(0);
            ivThumbnail.setImageResource(0);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context,ArticleActivity.class);
                i.putExtra("url",article.getUrl());
                context.startActivity(i);
            }
        });
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    boolean IsNullOrEmpty(String str) {
        if (str == null)
            return true;

        String trimmed = str.trim();
        if (trimmed.equals(""))
            return true;

        return false;
    }
}
