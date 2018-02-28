package com.ashish.feeds.domain.interactors.impl;

import com.ashish.feeds.domain.executor.Executor;
import com.ashish.feeds.domain.executor.MainThread;
import com.ashish.feeds.domain.interactors.GetFeedsInteractor;
import com.ashish.feeds.domain.interactors.base.AbstractInteractor;

/**
 * @author ashish
 * @since 28/02/18
 */
public class GetFeedsInteractorImpl extends AbstractInteractor implements GetFeedsInteractor {

    private Callback callback;

    public GetFeedsInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback) {
        super(threadExecutor, mainThread);

        if(callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
    }

    @Override
    public void run() {

    }
}
