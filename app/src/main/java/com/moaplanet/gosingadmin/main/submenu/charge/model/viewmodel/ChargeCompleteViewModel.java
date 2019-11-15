package com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.BaseViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.req.ReqCardChargeDto;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardChargeDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

/**
 * 충전 완료페이지 뷰 모델
 */
public class ChargeCompleteViewModel extends BaseViewModel {

    // --- Field Start --- //

    private MutableLiveData<ResCardChargeDto.PointDto> mPointDto = new MutableLiveData<>();

    // --- Field End --- //

    // --- Getter Start --- //

    public MutableLiveData<ResCardChargeDto.PointDto> getPointDto() {
        return mPointDto;
    }

    // --- Getter Start --- //

    // --- Setter Start --- //

    private void setPointDto(ResCardChargeDto.PointDto pointDto) {
        this.mPointDto.setValue(pointDto);
    }

    // --- Setter End --- //

    /**
     * 카드 충전 - 서버와 통신
     */
    public void onCardPointCharge(ReqCardChargeDto reqCardChargeDto) {
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerCardCharge(
                        reqCardChargeDto.getCardHashPk(),
                        reqCardChargeDto.getChargeMoney())
                .enqueue(new MoaAuthCallback<ResCardChargeDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResCardChargeDto> call, ResCardChargeDto resModel) {
                        setIsLoading(false);

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            setIsApiSuccess(true);
                            setPointDto(resModel.getPointDto());
                        } else {
                            setIsApiSuccess(false);
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResCardChargeDto> call, boolean isSession, Throwable t) {
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

}
