package com.moaplanet.gosingadmin.main.submenu.point;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * Created by jiwun on 2019-12-04.
 */
public class PointHistoryDataSorceFactory extends DataSource.Factory {

    private MutableLiveData<PointHistoryDataSource> dataSource = new MutableLiveData<>();

    // 날짜 관련
    private String startDate;
    private String endDate;
    private String pointType;

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @NonNull
    @Override
    public DataSource create() {
        PointHistoryDataSource pointHistoryDataSource = new PointHistoryDataSource();
        pointHistoryDataSource.setStartDate(startDate);
        pointHistoryDataSource.setEndDate(endDate);
        pointHistoryDataSource.setPointType(pointType);
        dataSource.postValue(pointHistoryDataSource);
        return pointHistoryDataSource;
    }

    public LiveData<PointHistoryDataSource> getDataSource() {
        return dataSource;
    }
}
