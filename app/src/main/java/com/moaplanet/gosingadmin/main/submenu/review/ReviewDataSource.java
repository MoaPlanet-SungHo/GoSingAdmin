package com.moaplanet.gosingadmin.main.submenu.review;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.review.model.ResReviewDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import retrofit2.Call;

/**
 * 리뷰 리스트 ReviewDataSource
 */
public class ReviewDataSource extends PageKeyedDataSource<Integer, ResReviewDTO.ReviewInfoModel> {

    // 가맹점 정보
    private MutableLiveData<ResReviewDTO.StoreInfoModel> mStoreInfoModel = new MutableLiveData<>();
    // 리뷰 리스트 유무
    private MutableLiveData<Boolean> mReviewEmpty = new MutableLiveData<>();

    private int mReviewType = GoSingConstants.BUNDLE_VALUE_REVIEW_LIST_ALL;

    public LiveData<ResReviewDTO.StoreInfoModel> getStoreInfoModel() {
        return mStoreInfoModel;
    }

    public LiveData<Boolean> getmReviewEmpty() {
        return mReviewEmpty;
    }

    public void setReviewType(int mReviewType) {
        this.mReviewType = mReviewType;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ResReviewDTO.ReviewInfoModel> callback) {
        RetrofitService.getInstance().getGoSingApiService()
                .onServerReviewLis(1, GoSingConstants.REVIEW_LIST_LIMIT, mReviewType)
                .enqueue(new MoaAuthCallback<ResReviewDTO>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResReviewDTO> call, ResReviewDTO resModel) {
                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {

                            if (resModel.getStoreInfoModel() != null) {
                                mStoreInfoModel.setValue(resModel.getStoreInfoModel());
                            }

                            if (resModel.getReviewList() != null && resModel.getReviewList().size() > 0) {
                                callback.onResult(resModel.getReviewList(), null, 2);
                            } else {
                                mReviewEmpty.setValue(true);
                            }
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResReviewDTO> call, boolean isSession, Throwable t) {
                        mReviewEmpty.setValue(true);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResReviewDTO.ReviewInfoModel> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResReviewDTO.ReviewInfoModel> callback) {
        RetrofitService.getInstance().getGoSingApiService()
                .onServerReviewLis(params.key, GoSingConstants.REVIEW_LIST_LIMIT, mReviewType)
                .enqueue(new MoaAuthCallback<ResReviewDTO>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResReviewDTO> call, ResReviewDTO resModel) {
                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {

                            if (resModel.getReviewList() != null && resModel.getReviewList().size() > 0) {
                                Logger.d("리스트 사이즈2 : " + resModel.getReviewList().size());
                                int key = params.key + 1;
                                callback.onResult(resModel.getReviewList(), key);
                            }
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResReviewDTO> call, boolean isSession, Throwable t) {

                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                    }
                });
    }

}
