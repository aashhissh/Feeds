package com.ashish.feeds.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author ashish
 * @since 28/02/18
 */
public class TimeLineResponseModel {
    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private List<FeedResponseModel> feeds;

    public String getTitle() {
        return title;
    }

    public List<FeedResponseModel> getFeeds() {
        return feeds;
    }
}
