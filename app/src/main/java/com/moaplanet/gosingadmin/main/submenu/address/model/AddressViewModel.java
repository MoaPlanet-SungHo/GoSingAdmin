package com.moaplanet.gosingadmin.main.submenu.address.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.moaplanet.gosingadmin.main.submenu.address.AddressDataSourceFactory;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;

public class AddressViewModel extends ViewModel {

    public LiveData<PagedList<ResAddressSearchDto.AddressInfoDto>> addressSearchList;

    @SuppressWarnings("unchecked")
    public void addressSearchInit(String keyword) {

        AddressDataSourceFactory dataSourceFactory = new AddressDataSourceFactory();
        dataSourceFactory.setKeyword(keyword);
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(NetworkConstants.ADDRESS_SEACH_PAGING_COUNT)
                .build();

        addressSearchList = new LivePagedListBuilder(dataSourceFactory, config).build();

    }
}
