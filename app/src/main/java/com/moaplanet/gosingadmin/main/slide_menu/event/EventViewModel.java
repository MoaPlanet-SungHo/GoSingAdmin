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

    // 세션 상태값 -> true 만료, false 유지되어있음
    private MutableLiveData<Boolean> expireSession = new MutableLiveData<>();

    // 통신 상태값 -> true 통신 실패, false 통신 성공
    private MutableLiveData<Boolean> failNetwork = new MutableLiveData<>();

    // 이벤트 리스트
    private MutableLiveData<List<EventDTO.EventModel>> eventList = new MutableLiveData<>();

    // 이벤트 베이스 url 주소
    private String baseEventUrl = "";

    public LiveData<Boolean> getExpireSession() {
        return expireSession;
    }

    public LiveData<Boolean> getFailNetwork() {
        return failNetwork;
    }

    public LiveData<List<EventDTO.EventModel>> getEventList() {
        return eventList;
    }

    /**
     * 이벤트 리스트 불러오기
     */
    public void postEventList(int pageNumber) {
        RetrofitService.getInstance().getGoSingApi()
                .postEventList(pageNumber, NetworkConstants.EVENT_LIMIT)
                .enqueue(new RetrofitCallBack<EventDTO>() {
                    @Override
                    public void onSuccess(EventDTO response) {
                        Logger.d("이벤트 리스트 : " + new Gson().toJson(response));
                        baseEventUrl = response.getEventUrl();
                        eventList.setValue(response.getEventList());
                    }

                    @Override
                    public void onFail(Response<EventDTO> response, Throwable t) {
                        failNetwork.setValue(true);
                    }

                    @Override
                    public void onExpireSession(Response<EventDTO> response, Throwable t) {
                        expireSession.setValue(true);
                    }
                });
    }

    public String getEventUrl(String seq) {
        return baseEventUrl + seq;
    }

}
