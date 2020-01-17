package com.moaplanet.gosingadmin.main.slide_menu.notice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Response;

/**
 * 공지사항 뷰모델
 */
public class NoticeViewModel extends BaseViewModel {

    // 공지사항 리스트
    private MutableLiveData<List<NoticeDTO.NoticeModel>> noticeList = new MutableLiveData<>();

    public LiveData<List<NoticeDTO.NoticeModel>> getNoticeList() {
        return noticeList;
    }

    /**
     * 공지사항 리스트
     */
    public void postNoticeList(int pageNumber) {

        RetrofitService.getInstance().getGoSingApi()
                .postNoticeList(pageNumber, NetworkConstants.NOTICE_LIMIT)
                .enqueue(new RetrofitCallBack<NoticeDTO>() {
                    @Override
                    public void onSuccess(NoticeDTO response) {
                        Logger.d("공지사항 데이터 : " + new Gson().toJson(response));
                        if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            noticeList.setValue(response.getNoticeList());
                        } else {
                            failNetwork.setValue(true);
                        }

                    }

                    @Override
                    public void onFail(Response<NoticeDTO> response, Throwable t) {
                        failNetwork.setValue(true);
                    }

                    @Override
                    public void onExpireSession(Response<NoticeDTO> response, Throwable t) {
                        expireSession.setValue(true);
                    }
                });

    }
}
