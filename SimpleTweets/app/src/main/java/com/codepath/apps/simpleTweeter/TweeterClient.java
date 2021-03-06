package com.codepath.apps.simpleTweeter;

import org.apache.http.Header;
import org.json.JSONObject;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.simpleTweeter.models.User;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TweeterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "QRX6jqdb78flLaWpv6PYxGox1";       // Change this
	public static final String REST_CONSUMER_SECRET = "I9STvejskn1wZDsAVfiY4woH3rw0e4UFNmi3aZUI3fOnKByig5"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://tweeter"; // Change this (here and in manifest)


	//private User authenticatedUser;

	public TweeterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getHomeTimeline(AsyncHttpResponseHandler handler, int page) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("page", page);
		// Can specify query string params directly or through RequestParams.
		client.get(apiUrl, params, handler);
	}
	public void getMentionsTimeline(AsyncHttpResponseHandler handler, int page) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("page", page);
		// Can specify query string params directly or through RequestParams.
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

	public void postTweet(String body, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", body);
		client.post(apiUrl, params, handler);
	}

	public void verifyCredentials(AsyncHttpResponseHandler handler) {
		//GET
		//https://api.twitter.com/1.1/account/verify_credentials.json
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, handler);
	}

	public void getUserTimeline(long user_id, int page, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 50);
		params.put("since_id", 1);
		params.put("contributor_details", true);

		if (user_id != 0){
			params.put("user_id", user_id);
		}

		if (page != 0) {
			params.put("page", page);
		}
		client.get(apiUrl, params, handler);
	}

	public void getUserFollowers(long user_id, AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl("followers/list.json");
		RequestParams params = new RequestParams();
		params.put("user_id",user_id);
		params.put("count",25);
		client.get(apiURL,params,handler);
	}
	public void getUserFollowing(long user_id, AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl("friends/list.json");
		RequestParams params = new RequestParams();
		params.put("user_id",user_id);
		params.put("count",25);
		client.get(apiURL,params,handler);
	}
	public void reTweet(String id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl(String.format("statuses/retweet/%s.json", id));
		client.post(apiUrl, handler);
	}

	public void getSearchResults (String q, AsyncHttpResponseHandler handler){
		String apiUrl =getApiUrl("search/tweets.json");
		RequestParams params = new RequestParams();
		params.put("q",q);
		client.get(apiUrl, params, handler);
	}
}