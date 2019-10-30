package com.moaplanet.gosingadmin.main.submenu.address;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

public class AddressSearchDataSource extends PageKeyedDataSource<Integer, ResAddressSearchDto.AddressInfoDto> {

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ResAddressSearchDto.AddressInfoDto> callback) {
//        RetrofitService.getInstance().getAddressApiService().
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResAddressSearchDto.AddressInfoDto> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ResAddressSearchDto.AddressInfoDto> callback) {

    }
}
