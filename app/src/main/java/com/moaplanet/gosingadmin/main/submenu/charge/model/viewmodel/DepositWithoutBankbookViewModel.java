package com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.BaseViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResRegisterVirtualAccountDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResSearchVirtualAccountDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

/**
 * 가상계좌 뷰모델
 */
public class DepositWithoutBankbookViewModel extends BaseViewModel {

    // --- Field Start--- //

    private MutableLiveData<ResSearchVirtualAccountDto.VirtualAccountDto> mVirtualAccountDto
            = new MutableLiveData<>();

    // --- Field End--- //

    // --- Getter Start--- //

    public LiveData<ResSearchVirtualAccountDto.VirtualAccountDto> getVirtualAccountDto() {
        return mVirtualAccountDto;
    }


    // --- Getter End--- //

    // --- Setter Start--- //

    public void setVirtualAccountDto(ResSearchVirtualAccountDto.VirtualAccountDto virtualAccountDto) {
        this.mVirtualAccountDto.setValue(virtualAccountDto);
    }

    // --- Setter End--- //

    // --- Other Method Start --- //

    /**
     * 가상 계좌 조회 api 호출
     */
    public void onSearchVirtualAccount() {
        setIsLoading(true);
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerSearchVirtualAccount()
                .enqueue(new MoaAuthCallback<ResSearchVirtualAccountDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResSearchVirtualAccountDto> call,
                                                ResSearchVirtualAccountDto resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            setVirtualAccountDto(resModel.getVirtualAccountDto());
                        } else {
                            setIsApiSuccess(false);
                        }
                        setIsLoading(false);


                    }

                    @Override
                    public void onFinalFailure(Call<ResSearchVirtualAccountDto> call,
                                               boolean isSession, Throwable t) {
                        setIsLoading(false);
                        setIsApiSuccess(false);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        setIsLoading(false);
                        setSession(false);
                    }
                });
    }

    /**
     * 가상계좌 발급
     */
    public void onRegisterVirtualAccount() {
        setIsLoading(true);
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerRegisterVirtualAccount()
                .enqueue(new MoaAuthCallback<ResRegisterVirtualAccountDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResRegisterVirtualAccountDto> call,
                                                ResRegisterVirtualAccountDto resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            onSearchVirtualAccount();
                        } else {
                            setIsApiSuccess(false);
                            setIsLoading(false);
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResRegisterVirtualAccountDto> call,
                                               boolean isSession, Throwable t) {

                        setIsLoading(false);
                        setIsApiSuccess(false);

                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        setIsLoading(false);
                        setSession(false);
                    }
                });
    }

    // --- Other Method End --- //

}
