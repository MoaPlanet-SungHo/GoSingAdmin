package com.moaplanet.gosingadmin.main.slide_menu.main.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.BaseViewModel;
import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResSearchDepositAccount;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;

import retrofit2.Call;

public class MainViewModel extends BaseViewModel {

    private MutableLiveData<String> pointGoSing = new MutableLiveData<>();
    private MutableLiveData<String> pointExpectedActive = new MutableLiveData<>();

    // 출금 계좌 정보
    private MutableLiveData<ResSearchDepositAccount.DepositAccount> mDepositAccount =
            new MutableLiveData<>();

    public void setPoint(ResPointDto pointDto) {
        pointGoSing.setValue(
                StringUtil.convertCommaPrice(pointDto.getPoint()));
        pointExpectedActive.setValue(
                StringUtil.convertCommaPrice(pointDto.getPointExpectedActive()));
    }

    public void setDepositAccount(ResSearchDepositAccount.DepositAccount mDepositAccount) {
        this.mDepositAccount.setValue(mDepositAccount);
    }

    public LiveData<ResSearchDepositAccount.DepositAccount> getDepositAccount() {
        return mDepositAccount;
    }

    public LiveData<String> getPointExpectedActive() {
        return pointExpectedActive;
    }

    public LiveData<String> getPointGoSing() {
        return pointGoSing;
    }

    public void onSearchDepositAccount() {

        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerSearchDepositAccount()
                .enqueue(new MoaAuthCallback<ResSearchDepositAccount>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResSearchDepositAccount> call,
                                                ResSearchDepositAccount resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            mDepositAccount.setValue(resModel.getDepositAccount());
                        } else {
                            setIsApiSuccess(false);
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResSearchDepositAccount> call,
                                               boolean isSession, Throwable t) {
                        setIsApiSuccess(false);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();

                        setSession(false);

                    }
                });

    }
}
