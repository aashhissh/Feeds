package com.ashish.feeds.presentation.presenters.impl;

import com.ashish.feeds.domain.executor.Executor;
import com.ashish.feeds.domain.executor.MainThread;
import com.ashish.feeds.domain.interactors.SampleInteractor;
import com.ashish.feeds.presentation.presenters.MainPresenter;
import com.ashish.feeds.presentation.presenters.base.AbstractPresenter;

/**
 * Created by dmilicic on 12/13/15.
 */
public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        SampleInteractor.Callback {

    private MainPresenter.View mView;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view) {
        super(executor, mainThread);
        mView = view;
    }

    @Override
    public void resume() {
        mView.showProgress();
        getFeedsDataFromServer();
    }

    private void getFeedsDataFromServer() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
