package com.ashish.feeds.network.service;

import com.ashish.feeds.network.response.FeedResponseModel;
import com.ashish.feeds.network.response.TimeLineResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * @author ashish
 * @since 28/02/18
 */
public interface SyncService {

    @GET("facts.json")
    Call<TimeLineResponseModel> fetchFeeds();

}
