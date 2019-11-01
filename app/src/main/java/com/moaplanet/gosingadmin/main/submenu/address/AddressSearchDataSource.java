package com.moaplanet.gosingadmin.main.submenu.address;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.moaplanet.gosingadmin.main.submenu.address.model.req.ReqAddressSearchDto;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressSearchDataSource extends PageKeyedDataSource<Integer, ResAddressSearchDto.AddressInfoDto> {

    private ReqAddressSearchDto reqAddressSearchDto = new ReqAddressSearchDto();
    private String keyword;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEmptyData = new MutableLiveData<>();
    private MutableLiveData<String> allAddress = new MutableLiveData<>();

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LiveData<Boolean> getIsEmptyData() {
        return isEmptyData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getAllAddress() {
        return allAddress;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer,
                                    ResAddressSearchDto.AddressInfoDto> callback) {

        isLoading.postValue(true);

        reqAddressSearchDto.setCurrentPage(1);
        reqAddressSearchDto.setKeyword(keyword);
        RetrofitService.getInstance().getAddressApiService()
                .searchAddress(reqAddressSearchDto.getConfmKey(),
                        reqAddressSearchDto.getCurrentPage(),
                        reqAddressSearchDto.getCountPerPage(),
                        reqAddressSearchDto.getKeyword(),
                        reqAddressSearchDto.getResultType())
                .enqueue(new Callback<ResAddressSearchDto>() {
                    @Override
                    public void onResponse(@NonNull Call<ResAddressSearchDto> call,
                                           @NonNull Response<ResAddressSearchDto> response) {

                        if (response.body() != null) {
                            if (response.body().getAddressInfoDtoList() != null
                                    && response.body().getAddressInfoDtoList().size() != 0) {

                                callback.onResult(response.body().getAddressInfoDtoList(),
                                        null,
                                        reqAddressSearchDto.getCurrentPage() + 1);
                                isEmptyData.postValue(false);

                                if (response.body().getAddressCommonDto() != null &&
                                        response.body().getAddressCommonDto().getTotalCount() != null) {
                                    allAddress.postValue(response.body().getAddressCommonDto().getTotalCount());
                                }
                            } else {
                                isEmptyData.postValue(true);
                            }

                        } else {
                            isEmptyData.postValue(true);
                        }

                        isLoading.postValue(false);
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResAddressSearchDto> call,
                                          @NonNull Throwable t) {
                        isEmptyData.postValue(true);
                        isLoading.postValue(false);
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResAddressSearchDto.AddressInfoDto> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResAddressSearchDto.AddressInfoDto> callback) {

        reqAddressSearchDto.setCurrentPage(params.key);

        RetrofitService.getInstance().getAddressApiService()
                .searchAddress(reqAddressSearchDto.getConfmKey(),
                        reqAddressSearchDto.getCurrentPage(),
                        reqAddressSearchDto.getCountPerPage(),
                        reqAddressSearchDto.getKeyword(),
                        reqAddressSearchDto.getResultType())
                .enqueue(new Callback<ResAddressSearchDto>() {
                    @Override
                    public void onResponse(@NonNull Call<ResAddressSearchDto> call,
                                           @NonNull Response<ResAddressSearchDto> response) {

                        if (response.body() != null) {
                            int key = params.key + 1;
                            callback.onResult(response.body().getAddressInfoDtoList(), key);
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<ResAddressSearchDto> call,
                                          @NonNull Throwable t) {

                    }
                });
    }
}
