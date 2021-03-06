package com.rakhee.codepath.nytimesarticle.Activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rakhee.codepath.nytimesarticle.Adapter.ArticleAdapter;
import com.rakhee.codepath.nytimesarticle.Model.Article;
import com.rakhee.codepath.nytimesarticle.R;
import com.rakhee.codepath.nytimesarticle.Model.SearchFilters;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {

    ArrayList<Article> mArticles;
    ArticleAdapter mAdapter;
    int page;
    String query;
    boolean isFetching;
    TextView tvBeginSearch;

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
        final StaggeredGridLayoutManager slm= new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(slm);
        rvArticles.setItemAnimator(new SlideInUpAnimator());
        rvArticles.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public int getLastVisibleItem(int[] lastVisibleItemPositions) {
                int maxSize = 0;
                for (int i = 0; i < lastVisibleItemPositions.length; i++) {
                    if (i == 0) {
                        maxSize = lastVisibleItemPositions[i];
                    } else if (lastVisibleItemPositions[i] > maxSize) {
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

        tvBeginSearch = (TextView) findViewById(R.id.tvBeginSearch);
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    private void fetchMore() {

        if (!isOnline()) {
            Toast.makeText(MainActivity.this, "Network unavailable. Try again later.", Toast.LENGTH_LONG).show();
            return;
        }

        isFetching = true;

        String url="http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.add("q", query);
        params.add("api-key", "14ea6144682ebe934eb6237f8edb9049:1:74337110");
        params.add("page", String.valueOf(page));

        SharedPreferences pref = getSharedPreferences("NYTimesArticleSearch", Context.MODE_PRIVATE);
        String filtersStr = pref.getString("SearchFilters", null);
        if (filtersStr != null) {
            SearchFilters filters = new Gson().fromJson(filtersStr, SearchFilters.class);
            if (filters.beginDate != null) {
                params.add("begin_date", new SimpleDateFormat("yyyyMMdd").format(filters.beginDate));
            }

            if (filters.newsDeskValues != null && filters.newsDeskValues.size() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("news_desk:(");
                for (int i = 0; i < filters.newsDeskValues.size(); i++){
                    sb.append("\"");
                    sb.append(filters.newsDeskValues.get(i));
                    sb.append("\"");
                    if (i != filters.newsDeskValues.size() - 1) {
                        sb.append(" ");
                    }
                }
                sb.append(")");
                params.add("fq", sb.toString());
            }

            params.add("sort", filters.sortOrder);
        }

        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // parse response
                try {
                    mArticles.addAll(Article.fromJSONArray(response.getJSONObject("response").getJSONArray("docs")));
                    mAdapter.notifyDataSetChanged();
                    isFetching = false;

                    tvBeginSearch.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(MainActivity.this, "Network request failed", Toast.LENGTH_LONG).show();
                isFetching = false;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Network request failed", Toast.LENGTH_LONG).show();
                isFetching = false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_search,menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                FragmentManager fm = getFragmentManager();
                SettingsDialog settingsDialog = SettingsDialog.newInstance();
                settingsDialog.show(fm, "fragment_settings_dialog");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
