package com.ashish.feeds.presentation.presenters;

import com.ashish.feeds.presentation.models.FeedModel;
import com.ashish.feeds.presentation.presenters.base.BasePresenter;
import com.ashish.feeds.presentation.ui.BaseView;

import java.util.List;

public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        boolean isConnectedToInternet();

        void updateTitle(String title);

        void updateFeedsList(List<FeedModel> feeds);
    }

}
