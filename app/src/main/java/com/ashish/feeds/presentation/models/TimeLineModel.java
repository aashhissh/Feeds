package com.ashish.feeds.presentation.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ashish
 * @since 28/02/18
 */
public class TimeLineModel {

    private String title;
    private List<FeedModel> feeds;

    public TimeLineModel(String title, List<FeedModel> feeds) {
        this.title = title;
        this.feeds = feeds;
    }

    public String getTitle() {
        return title;
    }

    public List<FeedModel> getFeeds() {
        return feeds;
    }
}
