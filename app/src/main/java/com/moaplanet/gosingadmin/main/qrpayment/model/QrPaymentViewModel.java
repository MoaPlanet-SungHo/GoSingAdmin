package com.moaplanet.gosingadmin.main.qrpayment.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.main.qrpayment.dto.req.ReqCreateQrCodeDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResCreateQrCodeDto;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResQrCodeCheckDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

public class QrPaymentViewModel extends BaseViewModel {

    // 가맹점 이름
    private MutableLiveData<String> storeName = new MutableLiveData<>();
    // QrCode 이미지
    private MutableLiveData<String> qrCodeUrl = new MutableLiveData<>();
    // 통신 결과 --> true 성공 | false 실패
    private MutableLiveData<Boolean> connectServerResult = new MutableLiveData<>();
    // 결제 됬는지 체크 값 => true : 결제 성공 | false 결제 실패
    private MutableLiveData<Boolean> isPaymentSuccess = new MutableLiveData<>();
    // qrCode Seq 값
    private String qrCodeSeq = "";

    public LiveData<String> getStoreName() {
        return storeName;
    }

    public LiveData<String> getQrCodeUrl() {
        return qrCodeUrl;
    }

    public LiveData<Boolean> getConnectServerResult() {
        return connectServerResult;
    }

    public MutableLiveData<Boolean> getIsPaymentSuccess() {
        return isPaymentSuccess;
    }

    public void onCreateQrCode(ReqCreateQrCodeDto reqCreateQrCodeDto) {

        RetrofitService.getInstance().getGoSingApiService().onServerCreateQrCode(
                reqCreateQrCodeDto.getQrCodePk(),
                reqCreateQrCodeDto.getReservePrice(),
                reqCreateQrCodeDto.getNoReservePrice()
        ).enqueue(new MoaAuthCallback<ResCreateQrCodeDto>(
                RetrofitService.getInstance().getMoaAuthConfig(),
                RetrofitService.getInstance().getSessionChecker()
        ) {
            @Override
            public void onFinalResponse(Call<ResCreateQrCodeDto> call,
                                        ResCreateQrCodeDto resModel) {

                if (resModel != null &&
                        resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS &&
                        resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {

                    qrCodeSeq = resModel.getQrCodeSeq();
                    qrCodeUrl.setValue(resModel.getPathQrCode());
                    storeName.setValue(resModel.getStoreName());
                    connectServerResult.setValue(true);

                } else {
                    connectServerResult.setValue(false);
                }

            }

            @Override
            public void onFinalFailure(Call<ResCreateQrCodeDto> call,
                                       boolean isSession, Throwable t) {
                connectServerResult.setValue(false);
            }

            @Override
            public void onFinalNotSession() {
                super.onFinalNotSession();
                setSession(false);
            }
        });
    }

    public void onQrCodeCheck() {

        if (!qrCodeSeq.equals("")) {
            RetrofitService.getInstance().getGoSingApiService().onServerCreateQrCodeCheck(
                    qrCodeSeq
            ).enqueue(new MoaAuthCallback<ResQrCodeCheckDTO>(
                    RetrofitService.getInstance().getMoaAuthConfig(),
                    RetrofitService.getInstance().getSessionChecker()
            ) {
                @Override
                public void onFinalResponse(Call<ResQrCodeCheckDTO> call,
                                            ResQrCodeCheckDTO resModel) {
                    isPaymentSuccess.setValue(resModel.getIsPayment());
                }

                @Override
                public void onFinalFailure(Call<ResQrCodeCheckDTO> call,
                                           boolean isSession, Throwable t) {
                    connectServerResult.setValue(false);
                }

                @Override
                public void onFinalNotSession() {
                    super.onFinalNotSession();
                    setSession(false);
                }
            });
        }
    }
}
