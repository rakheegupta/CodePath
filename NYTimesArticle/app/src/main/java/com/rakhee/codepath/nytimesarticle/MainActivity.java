package com.rakhee.codepath.nytimesarticle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {

    ArrayList<Article> mArticles;
    ArticleAdapter mAdapter;
    int page;
    String query;
    boolean isFetching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = 0;
        isFetching = false;
        // Lookup the recyclerview in activity layout
        RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        // Create adapter passing in the sample user data
        mArticles = new ArrayList<>();
        mAdapter = new ArticleAdapter(mArticles);
        // Attach the adapter to the recyclerview to populate items
        rvArticles.setAdapter(mAdapter);
        // Set layout manager to position the items
        final StaggeredGridLayoutManager slm= new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(slm);
        rvArticles.setItemAnimator(new SlideInUpAnimator());
        rvArticles.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public int getLastVisibleItem(int[] lastVisibleItemPositions) {
                int maxSize = 0;
                for (int i = 0; i < lastVisibleItemPositions.length; i++) {
                    if (i == 0) {
                        maxSize = lastVisibleItemPositions[i];
                    }
                    else if (lastVisibleItemPositions[i] > maxSize) {
                        maxSize = lastVisibleItemPositions[i];
                    }
                }
                return maxSize;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int[] lastVisibleItemPositions = slm.findLastVisibleItemPositions(null);
                int lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
                int totalItemCount = slm.getItemCount();

                if (totalItemCount > 0 && totalItemCount - lastVisibleItemPosition < 10) {
                    page++;
                    fetchMore();
                }
            }
        });

    }

    private void fetchMore() {
        isFetching = true;
        String url="http://api.nytimes.com/svc/search/v2/articlesearch.json?q="+query+"&api-key=14ea6144682ebe934eb6237f8edb9049:1:74337110&page=" + String.valueOf(page);
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // parse response
                try {
                    mArticles.addAll(Article.fromJSONArray(response.getJSONObject("response").getJSONArray("docs")));
                    mAdapter.notifyDataSetChanged();
                    isFetching = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(MainActivity.this, "Network request failed", Toast.LENGTH_LONG);
                isFetching = false;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Network request failed", Toast.LENGTH_LONG);
                isFetching = false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infalter = getMenuInflater();
        infalter.inflate(R.menu.action_search,menu);
        MenuItem searchItem =menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                mArticles.clear();
                page = 0;
                query = q;
                fetchMore();
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
