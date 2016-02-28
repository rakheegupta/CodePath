package com.codepath.apps.simpleTweeter.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.models.User;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class NewTweetActivity extends AppCompatActivity {
    EditText etTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
      //  getSupportActionBar().setCustomView(R.layout.action_bar_title);

        TextView tvName = (TextView) findViewById(R.id.tvUserName);
        User user=(User) Parcels.unwrap(getIntent().getParcelableExtra(TimelinesActivity.EXTRA_ADD_TWEET_MESSAGE));
        tvName.setText(user.getName());

        ImageView ivUserPhoto = (ImageView) findViewById(R.id.ivUserPhoto);
        //Picasso.with(this).load(user.getProfile_image_url()).into(ivUserPhoto);

        Glide.with(this)
                .load(user.getProfile_image_url())
                .bitmapTransform(new RoundedCornersTransformation(this, 4, 1, RoundedCornersTransformation.CornerType.ALL))
                .into(ivUserPhoto);
        etTweet = (EditText) findViewById(R.id.etTweet);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tweeted !!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // Close activity and send back new text
                String newTweetText = etTweet.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(TimelinesActivity.EXTRA_ADD_TWEET_RESULT, newTweetText);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
*/

        TextView tvScreenName = (TextView) findViewById(R.id.tvUserScreenName);
        tvScreenName.setText("@"+user.getScreen_name());

        EditText etTweetTExt = (EditText) findViewById(R.id.etTweet);
        if (getIntent().getBooleanExtra("retweet",false)) {
            etTweet.setText("@" + user.getScreen_name()+" ");
            etTweet.setSelection(("@" + user.getScreen_name()).length());
            etTweet.requestFocus();

        }
    }

    public void showSoftKeyboard(View view){
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }
    public void submitTweet(View v) {
        // Close activity and send back new text
        String newTweetText = etTweet.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(TimelinesActivity.EXTRA_ADD_TWEET_RESULT, newTweetText);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_tweet, menu);
        MenuItem composeItem =menu.findItem(R.id.miPostTweet);
        return true;
    }
}
