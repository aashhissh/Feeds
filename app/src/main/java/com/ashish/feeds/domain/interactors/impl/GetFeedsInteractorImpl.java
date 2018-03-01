package com.ashish.feeds.domain.interactors.impl;

import android.support.annotation.NonNull;

import com.ashish.feeds.domain.executor.Executor;
import com.ashish.feeds.domain.executor.MainThread;
import com.ashish.feeds.domain.interactors.GetFeedsInteractor;
import com.ashish.feeds.domain.interactors.base.AbstractInteractor;
import com.ashish.feeds.network.RestClient;
import com.ashish.feeds.network.response.FeedResponseModel;
import com.ashish.feeds.network.response.TimeLineResponseModel;
import com.ashish.feeds.network.service.SyncService;
import com.ashish.feeds.presentation.converters.ResponseModelConverter;

import java.io.IOException;

/**
 * @author ashish
 * @since 28/02/18
 */
public class GetFeedsInteractorImpl extends AbstractInteractor implements GetFeedsInteractor {

    private Callback callback;

    public GetFeedsInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback) {
        super(threadExecutor, mainThread);

        if (callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
    }

    public GetFeedsInteractorImpl(Executor threadExecutor, MainThread mainThread) {
        super(threadExecutor, mainThread);
    }

    @Override
    public void setCallback(Callback callback) {
        if (callback == null)
            throw new IllegalArgumentException("wtf bro");

        this.callback = callback;
    }

    @Override
    public void run() {
        // initializing the REST service we will use
        SyncService syncService = RestClient.getService(SyncService.class);

        try {
            final TimeLineResponseModel timeLineResponseModel = syncService.fetchFeeds().execute().body();

            if (timeLineResponseModel != null) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(
                                ResponseModelConverter.convertToTimeLineModel(
                                        timeLineResponseModel
                                )
                        );
                    }
                });
            } else {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError("Something went wrong !!!");
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
