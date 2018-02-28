package com.ashish.feeds;

import android.app.Application;

import com.ashish.feeds.network.RestClient;

public class FeedsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RestClient.init();
    }
}
