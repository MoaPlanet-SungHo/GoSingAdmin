package com.moaplanet.gosingadmin.main.submenu.address;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class AddressDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<AddressSearchDataSource> mutableLiveData = new MutableLiveData<>();
    private String keyword="";

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @NonNull
    @Override
    public DataSource create() {
        AddressSearchDataSource addressSearchDataSource = new AddressSearchDataSource();
        addressSearchDataSource.setKeyword(keyword);
        mutableLiveData.postValue(addressSearchDataSource);
        return addressSearchDataSource;
    }

}
