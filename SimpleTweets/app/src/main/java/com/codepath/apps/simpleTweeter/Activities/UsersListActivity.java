package com.codepath.apps.simpleTweeter.Activities;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.simpleTweeter.R;
import com.codepath.apps.simpleTweeter.TwitterApplication;
import com.codepath.apps.simpleTweeter.adapters.UserAdapter;
import com.codepath.apps.simpleTweeter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {
    ArrayList<User> mFollow;
    com.codepath.apps.simpleTweeter.adapters.UserAdapter userAdapter;
    ListView lvUsers;
    boolean inCall;
    Long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        userId=getIntent().getLongExtra("user_id", 0);
        getSupportActionBar().setTitle(getIntent().getStringExtra("users_type"));

        if (getIntent().getStringExtra("users_type").equals("Followers"))
            fetchAllFollowers();
        else if (getIntent().getStringExtra("users_type").equals("Following"))
            fetchAllFollowing();

        mFollow = new ArrayList<>();
        userAdapter = new UserAdapter(this,mFollow);
        lvUsers =(ListView) findViewById(R.id.lvUsersList);
        lvUsers.setAdapter(userAdapter);
    }
    public void fetchAllFollowing() {

        inCall = true;
        TwitterApplication.getRestClient().getUserFollowing(userId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG", "timeline: " + response.toString());
                try {
                    mFollow.addAll(User.fromJson(response.getJSONArray("users")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                userAdapter.notifyDataSetChanged();
                // Try to get more tweets only if we got something in this try
                // This is to ensure no infinite loop
                inCall = (mFollow.size() > 0);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                android.util.Log.e("tweets error", responseString);
                Toast.makeText(UsersListActivity.this, "Failed to get tweets", Toast.LENGTH_SHORT).show();
                inCall = false;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                android.util.Log.e("tweets error", errorResponse.toString());
                Toast.makeText(UsersListActivity.this, "Failed to get tweets", Toast.LENGTH_SHORT).show();
                inCall = false;
            }
        });
    }
    public void fetchAllFollowers() {

        inCall = true;
        TwitterApplication.getRestClient().getUserFollowers(userId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG", "timeline: " + response.toString());
                try{
                    mFollow.addAll(User.fromJson(response.getJSONArray("users")));
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                userAdapter.notifyDataSetChanged();
                // Try to get more tweets only if we got something in this try
                // This is to ensure no infinite loop
                inCall = (mFollow.size() > 0);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                android.util.Log.e("tweets error", responseString);
                Toast.makeText(UsersListActivity.this, "Failed to get tweets", Toast.LENGTH_SHORT).show();
                inCall = false;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                android.util.Log.e("tweets error", errorResponse.toString());
                Toast.makeText(UsersListActivity.this, "Failed to get tweets", Toast.LENGTH_SHORT).show();
                inCall = false;
            }
        });
    }
}