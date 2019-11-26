package com.moaplanet.gosingadmin.main.qrpayment.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseViewModel;
import com.moaplanet.gosingadmin.main.qrpayment.dto.res.ResPaymentInitDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;

import retrofit2.Call;

public class QrCodeViewModel extends BaseViewModel {

    private MutableLiveData<String> qrCodePk = new MutableLiveData<>();
    private MutableLiveData<Integer> reserveRatio = new MutableLiveData<>();
    private MutableLiveData<Boolean> serverConnectFail = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    // 적립 결제금액
    private MutableLiveData<String> inputSavePrice = new MutableLiveData<>();

    // 비적립 결제금액
    private MutableLiveData<String> inputNoSavePrice = new MutableLiveData<>();

    // 총 결제금액
    private MutableLiveData<String> totalPaymentPrice = new MutableLiveData<>();

    // 고객 적립금
    private MutableLiveData<String> saveMoney = new MutableLiveData<>();

    // 결제 후 GoSing포인트 잔액
    private MutableLiveData<Integer> paymentAfterPoint = new MutableLiveData<>();

    // 서버 로딩
    private MutableLiveData<Boolean> isLoadingServer = new MutableLiveData<>();

    public MutableLiveData<Integer> myPoint = new MutableLiveData<>();

    public LiveData<Integer> getReserveRatio() {
        return reserveRatio;
    }

    public LiveData<String> getQrCodePk() {
        return qrCodePk;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getServerConnectFail() {
        return serverConnectFail;
    }

    /**
     * 적립 결제금액 세팅
     *
     * @param inputSavePrice
     */
    public void setInputSavePrice(String inputSavePrice) {
        String priceValue = priceInit(inputSavePrice, this.inputSavePrice.getValue());
        if (!priceValue.equals("")) {
            this.inputSavePrice.setValue(priceValue);
        }
        totalPriceInit();
        saveMoneyInit();
    }

    public void setIsLoadingServer(boolean isLoadingServer) {
        this.isLoadingServer.setValue(isLoadingServer);
    }

    public LiveData<Boolean> getIsLoadingServer() {
        return isLoadingServer;
    }

    /**
     * 고객 적립금 초기화
     */
    private void saveMoneyInit() {
        if (inputSavePrice.getValue() != null && reserveRatio.getValue() != null) {
            String tempSavePrice = inputSavePrice.getValue().replace(",", "");
            if (!tempSavePrice.equals("")) {
                int savePriceResilt = Integer.valueOf(tempSavePrice) * reserveRatio.getValue() / 100;
                saveMoney.setValue(StringUtil.convertCommaPrice(savePriceResilt));
            }
        }
    }

    public LiveData<String> getSaveMoney() {
        return saveMoney;
    }

    /**
     * 비적립 결제금액 세팅
     *
     * @param inputNoSavePrice
     */
    public void setInputNoSavePrice(String inputNoSavePrice) {
        String priceValue = priceInit(inputNoSavePrice, this.inputNoSavePrice.getValue());
        if (!priceValue.equals("")) {
            this.inputNoSavePrice.setValue(priceValue);
        }
        totalPriceInit();
    }

    /**
     * 사용자가 입력한 금액을 콤마를 찍어서 반환
     * 공백일시 공백만 반환
     *
     * @param price 변경할 가격
     */
    private String priceInit(String price, String checkPrice) {
        if (!price.replaceAll("원", "").equals(checkPrice)) {
            String t = price.replaceAll("[,원]", "");
            if (t.equals("")) {
                return "0";
            } else {
                return StringUtil.convertCommaPrice(t);
            }
        }
        return "";
    }

    /**
     * 총 결제 금액 세팅
     */
    private void totalPriceInit() {
        // 적립 결제금액
        String savePrice = inputSavePrice.getValue();
        if (savePrice != null) {
            savePrice = savePrice.replaceAll("[,원]", "");
            if (savePrice.equals("")) {
                savePrice = "0";
            }
        } else {
            savePrice = "0";
        }
        // 비적립 결제금액
        String noSavePrice = inputNoSavePrice.getValue();
        if (noSavePrice != null) {
            noSavePrice = noSavePrice.replaceAll("[,원]", "");
            if (noSavePrice.equals("")) {
                noSavePrice = "0";
            }
        } else {
            noSavePrice = "0";
        }

        int totalPrice = Integer.valueOf(noSavePrice) + Integer.valueOf(savePrice);
        totalPaymentPrice.setValue(StringUtil.convertCommaPrice(totalPrice));

    }

    public LiveData<String> getTotalPaymentPrice() {
        return totalPaymentPrice;
    }

    public LiveData<String> getInputNoSavePrice() {
        return inputNoSavePrice;
    }

    public LiveData<String> getInputSavePrice() {
        return inputSavePrice;
    }

    public void onPaymentInfoInit() {
        loading.setValue(true);
        RetrofitService.getInstance().getGoSingApiService().onServerPaymentInit()
                .enqueue(new MoaAuthCallback<ResPaymentInitDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResPaymentInitDto> call,
                                                ResPaymentInitDto resModel) {

                        if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS
                                && resModel.getStateCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            qrCodePk.setValue(resModel.getPaymentInitDto().getQrCodePk());
                            reserveRatio.setValue(resModel.getPaymentInitDto().getReserveRatio());
                            myPoint.setValue(resModel.getPaymentInitDto().getGossingPoint());
                        } else {
                            qrCodePk.setValue(null);
                            reserveRatio.setValue(null);
                            serverConnectFail.setValue(true);
                        }

                        loading.setValue(false);

                    }

                    @Override
                    public void onFinalFailure(Call<ResPaymentInitDto> call,
                                               boolean isSession, Throwable t) {

                        qrCodePk.setValue(null);
                        reserveRatio.setValue(null);
                        serverConnectFail.setValue(true);
                        loading.setValue(false);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        qrCodePk.setValue(null);
                        reserveRatio.setValue(null);
                        serverConnectFail.setValue(true);
                        loading.setValue(false);
                        setSession(false);
                    }
                });
    }


}
