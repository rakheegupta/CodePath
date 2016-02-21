package com.codepath.apps.simpleTweeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTweeter.models.Tweet;
import com.codepath.apps.simpleTweeter.models.User;

public class ComposeActivity extends AppCompatActivity {
    Tweet newTweet;
    EditText etTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tweeted !!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // Close activity and send back new text
                String newTweetText = etTweet.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(TimelineActivity.EXTRA_ADD_TWEET_RESULT, newTweetText);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView tvName = (TextView) findViewById(R.id.tvUserName);
        User user=(User)getIntent().getSerializableExtra(TimelineActivity.EXTRA_ADD_TWEET_MESSAGE);
        tvName.setText(user.getName());

        ImageView ivUserPhoto = (ImageView) findViewById(R.id.ivUserPhoto);
        Glide.with(this).load(user.getProfilePicUrl()).into(ivUserPhoto);
        etTweet = (EditText) findViewById(R.id.etTweet);
    }
}
