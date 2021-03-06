package com.ashish.feeds.presentaion.presenters.impl;

import com.ashish.feeds.domain.executor.Executor;
import com.ashish.feeds.domain.executor.MainThread;
import com.ashish.feeds.domain.interactors.GetFeedsInteractor;
import com.ashish.feeds.domain.interactors.GetFeedsInteractor.Callback;
import com.ashish.feeds.domain.interactors.base.AbstractInteractor;
import com.ashish.feeds.domain.interactors.impl.GetFeedsInteractorImpl;
import com.ashish.feeds.presentation.models.FeedModel;
import com.ashish.feeds.presentation.models.TimeLineModel;
import com.ashish.feeds.presentation.presenters.MainPresenter;
import com.ashish.feeds.presentation.presenters.impl.MainPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * @author ashish
 * @since 01/03/18
 */
public class MainPresenterImplTest {

    private static TimeLineModel TIME_LINE_MODEL;

    @Mock
    private Executor mExecutor;

    @Mock
    private MainThread mMainThread;

    @Mock
    private MainPresenter.View mView;

    @Mock
    private Callback callback;

    @Captor
    private ArgumentCaptor<Callback> mGetFeedsCallbackCaptor;

    private MainPresenterImpl mMainPresenterImpl;

    @Mock
    private GetFeedsInteractor mGetFeedsInteractor;

    @Before
    public void setUpMainPresenterImpl() {
        MockitoAnnotations.initMocks(this);

        List<FeedModel> feeds = new ArrayList<>();
        feeds.add(new FeedModel("title", "description", "image"));
        feeds.add(new FeedModel("title1", "description1", "image1"));

        TIME_LINE_MODEL = new TimeLineModel("time line title", feeds);

        mMainPresenterImpl = new MainPresenterImpl(mExecutor, mMainThread, mView, mGetFeedsInteractor);
    }

    @Test
    public void checkOnError() {
        String errorMessage = "Something went wrong";
        mMainPresenterImpl.onError(errorMessage);

        verify(mView).hideProgress();
        verify(mView).showError(errorMessage);
    }

    @Test
    public void checkOnSuccess() {
        mMainPresenterImpl.onSuccess(TIME_LINE_MODEL);

        verify(mView).hideProgress();
        verify(mView).updateTitle(TIME_LINE_MODEL.getTitle());
        verify(mView).updateFeedsList(TIME_LINE_MODEL.getFeeds());
    }

    @Test
    public void getFeedsFromServer_Success() {
        mMainPresenterImpl.getFeedsDataFromServer();

        verify(mView).showProgress();
        verify(mGetFeedsInteractor).setCallback(mGetFeedsCallbackCaptor.capture());
        verify(mGetFeedsInteractor).execute();

        verify(mGetFeedsInteractor).setCallback(mGetFeedsCallbackCaptor.capture());
        mGetFeedsCallbackCaptor.getValue().onSuccess(TIME_LINE_MODEL);

        verify(mView).hideProgress();
        verify(mView).updateTitle(TIME_LINE_MODEL.getTitle());
        verify(mView).updateFeedsList(TIME_LINE_MODEL.getFeeds());
    }

    @Test
    public void getFeedsFromServer_Failure() {
        String errorMessage = "Something went wrong";
        mMainPresenterImpl.getFeedsDataFromServer();

        verify(mView).showProgress();
        verify(mGetFeedsInteractor).setCallback(mGetFeedsCallbackCaptor.capture());
        verify(mGetFeedsInteractor).execute();

        verify(mGetFeedsInteractor).setCallback(mGetFeedsCallbackCaptor.capture());
        mGetFeedsCallbackCaptor.getValue().onError(errorMessage);

        verify(mView).hideProgress();
        verify(mView).showError(errorMessage);
    }

}
