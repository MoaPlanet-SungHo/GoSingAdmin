package com.moaplanet.gosingadmin.main.submenu.address.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.moaplanet.gosingadmin.main.submenu.address.AddressDataSourceFactory;
import com.moaplanet.gosingadmin.main.submenu.address.AddressSearchDataSource;
import com.moaplanet.gosingadmin.main.submenu.address.model.res.ResAddressSearchDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;


public class AddressViewModel extends ViewModel {

    public LiveData<PagedList<ResAddressSearchDto.AddressInfoDto>> addressSearchList;
    private LiveData<Boolean> isLoading;
    private LiveData<Boolean> isEmptyData;
    private LiveData<String> allAddress;

    @SuppressWarnings("unchecked")
    public void addressSearchInit(String keyword) {

        AddressDataSourceFactory dataSourceFactory = new AddressDataSourceFactory();

        isLoading = Transformations.switchMap(dataSourceFactory.getDataSource(),
                AddressSearchDataSource::getIsLoading);
        isEmptyData = Transformations.switchMap(dataSourceFactory.getDataSource(),
                AddressSearchDataSource::getIsEmptyData);

        allAddress = Transformations.switchMap(dataSourceFactory.getDataSource(),
                AddressSearchDataSource::getAllAddress);

        dataSourceFactory.setKeyword(keyword);
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(NetworkConstants.ADDRESS_SEACH_PAGING_COUNT)
                .build();

        addressSearchList = new LivePagedListBuilder(dataSourceFactory, config).build();


    }

    public LiveData<Boolean> getIsEmptyData() {
        return isEmptyData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getAllAddress() {
        return allAddress;
    }
}
