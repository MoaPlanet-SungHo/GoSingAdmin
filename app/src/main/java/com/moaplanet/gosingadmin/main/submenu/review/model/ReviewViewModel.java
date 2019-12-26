package com.moaplanet.gosingadmin.main.submenu.review.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.review.ReviewDataFactory;
import com.moaplanet.gosingadmin.main.submenu.review.ReviewDataSource;

/**
 * 리뷰 뷰 모델
 */
public class ReviewViewModel extends BaseViewModel {

    private LiveData<PagedList<ResReviewDTO.ReviewInfoModel>> mReviewList;

    private LiveData<ResReviewDTO.StoreInfoModel> mStoreInfoModel;

    public LiveData<PagedList<ResReviewDTO.ReviewInfoModel>> getReviewList() {
        return mReviewList;
    }

    public LiveData<ResReviewDTO.StoreInfoModel> getStoreInfoModel() {
        return mStoreInfoModel;
    }

    private ReviewDataFactory mDataFactory;

    /**
     * 리뷰 리스트 불러오기
     */
    @SuppressWarnings("unchecked")
    public void getReviewList(int reviewType) {

        mDataFactory = new ReviewDataFactory();
        mDataFactory.setReviewType(reviewType);

        mStoreInfoModel = Transformations.switchMap(mDataFactory.getDataSrouce(),
                ReviewDataSource::getStoreInfoModel);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(GoSingConstants.REVIEW_LIST_LIMIT)
                .build();

        mReviewList = new LivePagedListBuilder(mDataFactory, config).build();

    }

    public void onRefresh() {
        if (mDataFactory != null) {
            if (mDataFactory.getDataSrouce().getValue() != null) {
                mDataFactory.getDataSrouce().getValue().invalidate();
            }
        }
    }

}
