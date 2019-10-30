package com.moaplanet.gosingadmin.main.submenu.address;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class AddressDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<AddressSearchDataSource> mutableLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        AddressSearchDataSource addressSearchDataSource = new AddressSearchDataSource();
        mutableLiveData.postValue(addressSearchDataSource);
        return addressSearchDataSource;
    }

}
