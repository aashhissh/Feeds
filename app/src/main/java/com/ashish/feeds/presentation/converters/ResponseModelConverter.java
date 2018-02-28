package com.ashish.feeds.presentation.converters;

import com.ashish.feeds.network.response.FeedResponseModel;
import com.ashish.feeds.network.response.TimeLineResponseModel;
import com.ashish.feeds.presentation.models.FeedModel;
import com.ashish.feeds.presentation.models.TimeLineModel;
import com.ashish.feeds.utils.CodeUtil;

import java.util.ArrayList;

/**
 * @author ashish
 * @since 28/02/18
 */
public class ResponseModelConverter {

    public static TimeLineModel convertToTimeLineModel(TimeLineResponseModel timeLineResponseModel) {
        ArrayList<FeedModel> feeds = new ArrayList<>();
        for(FeedResponseModel item : timeLineResponseModel.getFeeds()) {
            if(!CodeUtil.isEmpty(item.getTitle()) || !CodeUtil.isEmpty(item.getDescription()) || !CodeUtil.isEmpty(item.getImageHref())) {
                feeds.add(
                        new FeedModel(
                                item.getTitle(),
                                item.getDescription(),
                                item.getImageHref()
                        )
                );
            }
        }

        return new TimeLineModel(timeLineResponseModel.getTitle(), feeds);
    }

}
