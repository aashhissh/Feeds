package com.ashish.feeds.presentation.presenters;

import com.ashish.feeds.presentation.presenters.base.BasePresenter;
import com.ashish.feeds.presentation.ui.BaseView;

public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        boolean isConnectedToInternet();

        void setTitle(String title);

        void renderFeeds();
    }

}
