package com.ashish.feeds.domain.interactors;

import com.ashish.feeds.domain.interactors.base.Interactor;
import com.ashish.feeds.network.response.TimeLineResponseModel;
import com.ashish.feeds.presentation.models.TimeLineModel;

/**
 * @author ashish
 * @since 28/02/18
 */
public interface GetFeedsInteractor extends Interactor {

    interface Callback {
        void onError(String message);

        void onSuccess(TimeLineModel timeLineModel);
    }

}
