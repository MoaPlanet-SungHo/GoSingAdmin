package com.moaplanet.gosingadmin.main.submenu.address;

import androidx.annotation.NonNull;
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

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer,
                                    ResAddressSearchDto.AddressInfoDto> callback) {

        reqAddressSearchDto.setCurrentPage(1);
//        reqAddressSearchDto.setKeyword("가산");
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
                            callback.onResult(response.body().getAddressInfoDtoList(),
                                    null,
                                    reqAddressSearchDto.getCurrentPage() + 1);

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResAddressSearchDto> call,
                                          @NonNull Throwable t) {

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

                    }

                    @Override
                    public void onFailure(@NonNull Call<ResAddressSearchDto> call,
                                          @NonNull Throwable t) {

                    }
                });
    }
}