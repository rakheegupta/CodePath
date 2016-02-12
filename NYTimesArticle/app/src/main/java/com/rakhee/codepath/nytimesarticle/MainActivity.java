package com.rakhee.codepath.nytimesarticle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ArrayList<Article> mArticles;
    ArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        // Create adapter passing in the sample user data
        mArticles = new ArrayList<>();
        mAdapter = new ArticleAdapter(mArticles);
        // Attach the adapter to the recyclerview to populate items
        rvArticles.setAdapter(mAdapter);
        // Set layout manager to position the items
        rvArticles.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infalter = getMenuInflater();
        infalter.inflate(R.menu.action_search,menu);
        MenuItem searchItem =menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String url="http://api.nytimes.com/svc/search/v2/articlesearch.json?q="+query+"&api-key="+"14ea6144682ebe934eb6237f8edb9049:1:74337110";
                AsyncHttpClient client=new AsyncHttpClient();
                client.get(url,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // parse response
                        try {
                            mArticles.addAll(Article.fromJSONArray(response.getJSONObject("response").getJSONArray("docs")));
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(MainActivity.this, "Network request failed", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "Network request failed", Toast.LENGTH_LONG);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
