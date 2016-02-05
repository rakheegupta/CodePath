package com.example.rakhe.instagramclient;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentFeedActivity extends AppCompatActivity {
    private ArrayList<Comment> mCommentFeed;
    private CommentAdapter mCommentAdapter;

    ListView mLvCommentFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_feed_activity);
        mCommentFeed=new ArrayList<Comment>();
        mCommentAdapter=new CommentAdapter(this, mCommentFeed);
        mLvCommentFeed = (ListView)findViewById(R.id.lvCommentsFeed);
        mLvCommentFeed.setAdapter(mCommentAdapter);

        Photo photo=(Photo)getIntent().getSerializableExtra("photo");
        mCommentFeed.addAll(photo.getAllComments());
        mCommentAdapter.notifyDataSetChanged();
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
