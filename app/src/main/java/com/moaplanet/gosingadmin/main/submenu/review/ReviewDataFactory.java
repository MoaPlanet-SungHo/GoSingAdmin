package com.moaplanet.gosingadmin.main.submenu.review;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.moaplanet.gosingadmin.constants.GoSingConstants;

/**
 * 리뷰 리스트 ReviewDataFactory
 */
public class ReviewDataFactory extends DataSource.Factory {

    private MutableLiveData<ReviewDataSource> mDataSource = new MutableLiveData<>();

    private int mReviewType = GoSingConstants.BUNDLE_VALUE_REVIEW_LIST_ALL;

    @NonNull
    @Override
    public DataSource create() {

        ReviewDataSource dataSource = new ReviewDataSource();
        dataSource.setReviewType(mReviewType);
        mDataSource.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<ReviewDataSource> getDataSrouce() {
        return mDataSource;
    }

    public void setReviewType(int mReviewType) {
        this.mReviewType = mReviewType;
    }
}
