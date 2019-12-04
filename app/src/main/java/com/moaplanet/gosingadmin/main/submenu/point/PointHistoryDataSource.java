package com.moaplanet.gosingadmin.main.submenu.point;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.moaplanet.gosingadmin.main.submenu.point.model.dto.ResPointHistoryDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

/**
 * Created by jiwun on 2019-12-04.
 */
public class PointHistoryDataSource extends PageKeyedDataSource<Integer, ResPointHistoryDto.PointHistoryDto> {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEmptyData = new MutableLiveData<>();
    private MutableLiveData<Boolean> noSession = new MutableLiveData<>();
    private MutableLiveData<Boolean> networkFail = new MutableLiveData<>();

    public LiveData<Boolean> getIsEmptyData() {
        return isEmptyData;
    }

    // 날짜 관련
    private String startDate;
    private String endDate;
    private String pointType;

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getNoSession() {
        return noSession;
    }

    public MutableLiveData<Boolean> getNetworkFail() {
        return networkFail;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer,
                                    ResPointHistoryDto.PointHistoryDto> callback) {
        isLoading.postValue(true);

        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerPointHistoryList(
                        startDate,
                        endDate,
                        pointType,
                        1,
                        100)
                .enqueue(new MoaAuthCallback<ResPointHistoryDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResPointHistoryDto> call, ResPointHistoryDto resModel) {
//                        mLoadingBar.setVisibility(View.GONE);
                        isLoading.postValue(false);
                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {

                            if (resModel.getPointHistoryDtoList() != null &&
                                    resModel.getPointHistoryDtoList().size() > 0) {
                                isEmptyData.setValue(false);
                                callback.onResult(resModel.getPointHistoryDtoList(),
                                        null,
                                        2);
//                                viewPointListEmpty.setVisibility(View.GONE);
                            } else {
                                isEmptyData.setValue(true);
//                                viewPointListEmpty.setVisibility(View.VISIBLE);
                            }
//                            mAdapter.setList(resModel.getPointHistoryDtoList());
                        } else {
                            isEmptyData.postValue(true);
//                            onNetworkConnectFail();
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResPointHistoryDto> call, boolean isSession, Throwable t) {
//                        mLoadingBar.setVisibility(View.GONE);
//                        onNetworkConnectFail();
                        isLoading.postValue(false);
                        isEmptyData.postValue(true);
                        networkFail.postValue(true);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
//                        mLoadingBar.setVisibility(View.GONE);
//                        onNotSession();
                        isLoading.postValue(false);
                        noSession.postValue(true);
                    }
                });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResPointHistoryDto.PointHistoryDto> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResPointHistoryDto.PointHistoryDto> callback) {
        isLoading.postValue(true);

        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerPointHistoryList(
                        startDate,
                        endDate,
                        pointType,
                        params.key,
                        100)
                .enqueue(new MoaAuthCallback<ResPointHistoryDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResPointHistoryDto> call, ResPointHistoryDto resModel) {
//                        mLoadingBar.setVisibility(View.GONE);
                        isLoading.postValue(false);
                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {

                            if (resModel.getPointHistoryDtoList() != null &&
                                    resModel.getPointHistoryDtoList().size() > 0) {
                                int key = params.key + 1;
                                callback.onResult(resModel.getPointHistoryDtoList(), key);
//                                viewPointListEmpty.setVisibility(View.GONE);
                            } else {
//                                viewPointListEmpty.setVisibility(View.VISIBLE);
                            }
//                            mAdapter.setList(resModel.getPointHistoryDtoList());
                        } else {
//                            onNetworkConnectFail();
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResPointHistoryDto> call, boolean isSession, Throwable t) {
//                        mLoadingBar.setVisibility(View.GONE);
//                        onNetworkConnectFail();
                        isLoading.postValue(false);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
//                        mLoadingBar.setVisibility(View.GONE);
//                        onNotSession();
                        isLoading.postValue(false);
                        noSession.postValue(true);
                    }
                });
    }
}
