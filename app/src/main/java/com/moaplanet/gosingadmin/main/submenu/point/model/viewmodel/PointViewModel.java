package com.moaplanet.gosingadmin.main.submenu.point.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.moaplanet.gosingadmin.main.submenu.point.PointHistoryDataSorceFactory;
import com.moaplanet.gosingadmin.main.submenu.point.PointHistoryDataSource;
import com.moaplanet.gosingadmin.main.submenu.point.model.dto.ResPointHistoryDto;

/**
 * Created by jiwun on 2019-12-04.
 */
public class PointViewModel extends ViewModel {

    public LiveData<PagedList<ResPointHistoryDto.PointHistoryDto>> pointList;
    // 날짜 관련
    private String startDate;
    private String endDate;
    private String pointType;

    public LiveData<Boolean> isLoading;
    public LiveData<Boolean> isEmptyData;
    public LiveData<Boolean> noSession;
    public LiveData<Boolean> networkFail;

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @SuppressWarnings("unchecked")
    public void onPointPaging() {
        PointHistoryDataSorceFactory dataSourceFactory = new PointHistoryDataSorceFactory();

        isLoading = Transformations.switchMap(dataSourceFactory.getDataSource(),
                PointHistoryDataSource::getIsLoading);

        isEmptyData = Transformations.switchMap(dataSourceFactory.getDataSource(),
                PointHistoryDataSource::getIsEmptyData);

        noSession = Transformations.switchMap(dataSourceFactory.getDataSource(),
                PointHistoryDataSource::getIsEmptyData);

        networkFail = Transformations.switchMap(dataSourceFactory.getDataSource(),
                PointHistoryDataSource::getIsEmptyData);

        dataSourceFactory.setStartDate(startDate);
        dataSourceFactory.setEndDate(endDate);
        dataSourceFactory.setPointType(pointType);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(100)
                .build();
        pointList = new LivePagedListBuilder(dataSourceFactory, config).build();
    }
}
