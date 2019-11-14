package com.moaplanet.gosingadmin.main.submenu.charge.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaplanet.gosingadmin.common.model.BaseViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.List;

import retrofit2.Call;

/**
 * 카드 충전 뷰모델
 */
public class ChargeCardViewModel extends BaseViewModel {

    // -- Field Start -- //
    // 카드 리스트
    private MutableLiveData<List<ResCardListDto.CardInformationDto>> mCardInfoList =
            new MutableLiveData<>();

    // 선택된 카드
    private MutableLiveData<ResCardListDto.CardInformationDto> mSelectCardInfo =
            new MutableLiveData<>();

    // 사용자가 충전할 금액
    private MutableLiveData<String> mPriceCharge = new MutableLiveData<>();

    // 충전 버튼 활성화 유무 -- > true : 활성화 | false : 비활성화
    private MutableLiveData<Boolean> mChargeButtonActive = new MutableLiveData<>();
    // -- Field End -- //

    // --- Getter Start --- //
    public LiveData<List<ResCardListDto.CardInformationDto>> getCardInfoList() {
        return mCardInfoList;
    }

    public LiveData<ResCardListDto.CardInformationDto> getSelectCardInfo() {
        return mSelectCardInfo;
    }

    public LiveData<String> getPriceCharge() {
        return mPriceCharge;
    }

    public LiveData<Boolean> getChargeButtonActive() {
        return mChargeButtonActive;
    }
    // --- Getter End --- //

    // --- Setter Start--- //
    public void setSelectCardInfo(ResCardListDto.CardInformationDto selectCardInfo) {
        this.mSelectCardInfo.setValue(selectCardInfo);
    }

    public void setPriceCharge(String priceCharge) {

        boolean activeValue = false;
        if (!priceCharge.replaceAll("원", "").equals(mPriceCharge.getValue())) {
            String price = priceCharge.replaceAll("[,원]", "");
            if (price.equals("")) {
                price = "0";
            } else {
                if (Integer.valueOf(price) >= 1000) {
                    activeValue = true;
                }
                price = StringUtil.convertCommaPrice(price);
            }
            mPriceCharge.setValue(price);
        }
        mChargeButtonActive.setValue(activeValue);

    }
    // --- Setter End--- //

    // --- Init Start --- //

    /**
     * 카드 불러오기
     */
    public void onCardListInit() {
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerCardList()
                .enqueue(new MoaAuthCallback<ResCardListDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResCardListDto> call, ResCardListDto resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            setIsApiSuccess(true);
                            mCardInfoList.setValue(resModel.getCardInformationDtoList());
                        } else {
                            setExistSession(false);
                        }
                        setIsLoading(false);
                    }

                    @Override
                    public void onFinalFailure(Call<ResCardListDto> call,
                                               boolean isSession,
                                               Throwable t) {
                        setIsLoading(false);
                        setIsApiSuccess(false);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        setIsLoading(false);
                        setIsApiSuccess(false);
                        setExistSession(false);
                    }
                });
    }
    // --- Init End --- //
}
