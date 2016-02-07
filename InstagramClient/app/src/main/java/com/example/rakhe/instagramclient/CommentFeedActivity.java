package com.example.rakhe.instagramclient;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CommentFeedActivity extends AppCompatActivity {
    private ArrayList<Comment> mCommentFeed;
    private CommentAdapter mCommentAdapter;
    private Photo mPhoto;

    ListView mLvCommentFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_feed_activity);
        mCommentFeed=new ArrayList<Comment>();
        mCommentAdapter=new CommentAdapter(this, mCommentFeed);
        mLvCommentFeed = (ListView)findViewById(R.id.lvCommentsFeed);
        mLvCommentFeed.setAdapter(mCommentAdapter);

        mPhoto =(Photo)getIntent().getSerializableExtra("photo");
        fetchComments();
    }

    private void fetchComments() {
        AsyncHttpClient client = new AsyncHttpClient();

        String popularPhotoUrl= "https://api.instagram.com/v1/media/" + mPhoto.getId() + "/comments?client_id="+PhotoFeedActivity.CLIENT_ID;
        client.get(popularPhotoUrl, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    mCommentFeed.addAll(Photo.parseAllCommentsFromJSON(response.getJSONArray("data")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mCommentAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
