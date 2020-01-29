package com.moaplanet.gosingadmin.main.slide_menu.event;

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
 * 이벤트 뷰 모델
 */
public class EventViewModel extends BaseViewModel {

    // 이벤트 리스트
    private MutableLiveData<List<EventDTO.EventModel>> eventList = new MutableLiveData<>();

    // 이벤트 베이스 url 주소
    private String baseEventUrl = "";

    // 현재 페이지
    private int currentPage = 1;
    // 총 페이지
    private int totalPage = 1;
    // 서버 통신 플래그
    private boolean isPaging = false;

    LiveData<List<EventDTO.EventModel>> getEventList() {
        return eventList;
    }

    /**
     * 이벤트 리스트 불러오기
     *
     */
    void postEventList() {

        if (!isPaging && currentPage <= totalPage) {
            isPaging = true;
            RetrofitService.getInstance().getGoSingApi()
                    .postEventList(currentPage, NetworkConstants.EVENT_LIMIT)
                    .enqueue(new RetrofitCallBack<EventDTO>() {
                        @Override
                        public void onSuccess(EventDTO response) {
                            if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                                Logger.d("이벤트 리스트 : " + new Gson().toJson(response));
                                baseEventUrl = response.getEventUrl();
                                eventList.setValue(response.getEventList());
                                totalPage = response.getPageInfoModel().getTotalPage();
                                currentPage++;
                            } else {
                                failNetwork.setValue(true);
                            }
                            isPaging = false;
                        }

                        @Override
                        public void onFail(Response<EventDTO> response, Throwable t) {
                            failNetwork.setValue(true);
                            isPaging = false;
                        }

                        @Override
                        public void onExpireSession(Response<EventDTO> response, Throwable t) {
                            expireSession.setValue(true);
                            isPaging = false;
                        }
                    });
        }
    }

    String getEventUrl(String seq) {
        return baseEventUrl + seq;
    }

}
