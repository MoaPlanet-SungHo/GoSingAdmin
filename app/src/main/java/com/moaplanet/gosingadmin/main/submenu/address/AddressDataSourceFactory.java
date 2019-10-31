package com.moaplanet.gosingadmin.main.submenu.address;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class AddressDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<AddressSearchDataSource> dataSource = new MutableLiveData<>();
    private String keyword="";


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @NonNull
    @Override
    public DataSource create() {
        AddressSearchDataSource addressSearchDataSource = new AddressSearchDataSource();
        addressSearchDataSource.setKeyword(keyword);
        dataSource.postValue(addressSearchDataSource);
        return addressSearchDataSource;
    }

    public LiveData<AddressSearchDataSource> getDataSource() {
        return dataSource;
    }
}
