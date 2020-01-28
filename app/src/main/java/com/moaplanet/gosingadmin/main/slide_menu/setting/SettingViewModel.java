package com.moaplanet.gosingadmin.main.slide_menu.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.RetrofitCallBack;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Response;

public class SettingViewModel extends BaseViewModel {

    private final String ACTIVE_NOTIFICATION = "Y";

    // 앱 버전 문구
    private MutableLiveData<String> appVersionText = new MutableLiveData<>();

    // 고씽 혜택알림
    private MutableLiveData<Boolean> activeBenefit = new MutableLiveData<>();

    // 고씽 야간 알림
    private MutableLiveData<Boolean> activeNight = new MutableLiveData<>();

    public LiveData<String> getAppVersionText() {
        return appVersionText;
    }

    public LiveData<Boolean> getActiveBenefit() {
        return activeBenefit;
    }

    public LiveData<Boolean> getActiveNight() {
        return activeNight;
    }

    /**
     * 혜택알림 상태값 변경
     */
    public void postChangeBenefit(String state) {
        RetrofitService.getInstance().getGoSingApi()
                .postChangeBenefit(state)
                .enqueue(new RetrofitCallBack<SettingDTO>() {
                    @Override
                    public void onSuccess(SettingDTO response) {
                        if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            if (response.getBenefit().equals(ACTIVE_NOTIFICATION)) {
                                activeBenefit.setValue(true);
                            } else {
                                activeBenefit.setValue(false);
                            }
                        } else {
                            failNetwork.setValue(true);
                        }
                    }

                    @Override
                    public void onFail(Response<SettingDTO> response, Throwable t) {
                        failNetwork.setValue(true);
                    }

                    @Override
                    public void onExpireSession(Response<SettingDTO> response, Throwable t) {
                        expireSession.setValue(true);
                    }
                });
    }

    /**
     * 야간 상태값 변경
     */
    public void postChangeNight(String state) {
        RetrofitService.getInstance().getGoSingApi()
                .postChangeNight(state)
                .enqueue(new RetrofitCallBack<SettingDTO>() {
                    @Override
                    public void onSuccess(SettingDTO response) {
                        if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            if (response.getNight().equals(ACTIVE_NOTIFICATION)) {
                                activeNight.setValue(true);
                            } else {
                                activeNight.setValue(false);
                            }
                        } else {
                            failNetwork.setValue(true);
                        }
                    }

                    @Override
                    public void onFail(Response<SettingDTO> response, Throwable t) {
                        failNetwork.setValue(true);
                    }

                    @Override
                    public void onExpireSession(Response<SettingDTO> response, Throwable t) {
                        expireSession.setValue(true);
                    }
                });
    }

    public void postSetting() {
        RetrofitService.getInstance().getGoSingApi()
                .postSetting()
                .enqueue(new RetrofitCallBack<SettingDTO>() {
                    @Override
                    public void onSuccess(SettingDTO response) {

                        if (response.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            int versionCode = BuildConfig.VERSION_CODE;
                            if (versionCode == response.getAppVersion()) {
                                appVersionText.setValue("현재 최신 버전입니다.");
                            } else {
                                appVersionText.setValue("최신 버전으로 업데이트해 주세요.");
                            }

                            if (response.getBenefit().equals(ACTIVE_NOTIFICATION)) {
                                activeBenefit.setValue(true);
                            } else {
                                activeBenefit.setValue(false);
                            }

                            if (response.getNight().equals(ACTIVE_NOTIFICATION)) {
                                activeNight.setValue(true);
                            } else {
                                activeNight.setValue(false);
                            }

                        } else {
                            failNetwork.setValue(true);
                        }

                    }

                    @Override
                    public void onFail(Response<SettingDTO> response, Throwable t) {
                        failNetwork.setValue(true);
                    }

                    @Override
                    public void onExpireSession(Response<SettingDTO> response, Throwable t) {
                        expireSession.setValue(true);
                    }
                });
    }

}
