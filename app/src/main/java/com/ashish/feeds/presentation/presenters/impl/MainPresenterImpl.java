package com.ashish.feeds.presentation.presenters.impl;

import com.ashish.feeds.domain.executor.Executor;
import com.ashish.feeds.domain.executor.MainThread;
import com.ashish.feeds.domain.interactors.GetFeedsInteractor;
import com.ashish.feeds.domain.interactors.impl.GetFeedsInteractorImpl;
import com.ashish.feeds.network.response.TimeLineResponseModel;
import com.ashish.feeds.presentation.converters.ResponseModelConverter;
import com.ashish.feeds.presentation.models.TimeLineModel;
import com.ashish.feeds.presentation.presenters.MainPresenter;
import com.ashish.feeds.presentation.presenters.base.AbstractPresenter;

/**
 * @author ashish
 * @since 28/02/18
 */
public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        GetFeedsInteractor.Callback {

    private MainPresenter.View mView;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view) {
        super(executor, mainThread);
        mView = view;
    }

    @Override
    public void resume() {
        getFeedsDataFromServer();
    }

    @Override
    public void getFeedsDataFromServer() {
        mView.showProgress();
        GetFeedsInteractor getFeedsInteractor = new GetFeedsInteractorImpl(
                mExecutor,
                mMainThread,
                this
        );
        getFeedsInteractor.execute();
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
        mView.hideProgress();
        mView.showError(message);
    }

    @Override
    public void onSuccess(TimeLineModel timeLineModel) {
        mView.hideProgress();
        mView.updateTitle(timeLineModel.getTitle());
        mView.updateFeedsList(timeLineModel.getFeeds());
    }
}
