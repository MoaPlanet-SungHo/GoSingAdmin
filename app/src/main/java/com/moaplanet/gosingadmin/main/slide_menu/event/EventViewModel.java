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

    LiveData<List<EventDTO.EventModel>> getEventList() {
        return eventList;
    }

    /**
     * 이벤트 리스트 불러오기
     *
     * @param pageNumber 보여줄 페이지 번호
     */
    void postEventList(int pageNumber) {
        RetrofitService.getInstance().getGoSingApi()
                .postEventList(pageNumber, NetworkConstants.EVENT_LIMIT)
                .enqueue(new RetrofitCallBack<EventDTO>() {
                    @Override
                    public void onSuccess(EventDTO response) {
                        if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            Logger.d("이벤트 리스트 : " + new Gson().toJson(response));
                            baseEventUrl = response.getEventUrl();
                            eventList.setValue(response.getEventList());
                        } else {
                            failNetwork.setValue(true);
                        }
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

    String getEventUrl(String seq) {
        return baseEventUrl + seq;
    }

}
