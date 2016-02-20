package com.codepath.apps.simpleTweeter;

import android.content.Context;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TweeterClient client = TwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TwitterApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		com.codepath.apps.simpleTweeter.TwitterApplication.context = this;
	}

	public static TweeterClient getRestClient() {
		return (TweeterClient) TweeterClient.getInstance(com.codepath.apps.simpleTweeter.TweeterClient.class, com.codepath.apps.simpleTweeter.TwitterApplication.context);
	}
}