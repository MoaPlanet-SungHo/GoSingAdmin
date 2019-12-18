package com.moaplanet.gosingadmin.main.submenu.notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.main.submenu.notification.dto.ResNotificationDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;

/**
 * 알림 화면 뷰 모델
 */
public class NotificationViewModel extends BaseViewModel {

    // 알림 리스트
    private MutableLiveData<List<ResNotificationDTO.NotificationModel>> mNotificationList
            = new MutableLiveData<>();

    public LiveData<List<ResNotificationDTO.NotificationModel>> getNotificationList() {
        return mNotificationList;
    }

    /**
     * 알림 리스트 불러오기
     */
    public void onLoadNotificationList() {

        RetrofitService.getInstance().getGoSingApiService()
                .onServerNotificationList(null, null)
                .enqueue(new MoaAuthCallback<ResNotificationDTO>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResNotificationDTO> call, ResNotificationDTO resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            List<ResNotificationDTO.NotificationModel> list =
                                    resModel.getNotificationDtoList();

                            Logger.i("알림 리스트 alert_list : " + new Gson().toJson(list));
                            mNotificationList.postValue(list);

                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResNotificationDTO> call, boolean isSession, Throwable t) {
                        mIsApiSuccess.postValue(false);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        mSession.postValue(false);
                    }
                });
    }

}
