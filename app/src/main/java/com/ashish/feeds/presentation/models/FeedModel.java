package com.ashish.feeds.presentation.models;

/**
 * @author ashish
 * @since 28/02/18
 */
public class FeedModel {

    private String title;
    private String description;
    private String imageUrl;

    public FeedModel(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
