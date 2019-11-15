package com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.moaplanet.gosingadmin.common.model.BaseViewModel;
import com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res.ResCardListDto;
import com.moaplanet.gosingadmin.utils.StringUtil;

/**
 * 충전 뷰모델
 */
public class ChargeViewModel extends BaseViewModel {

    // --- Field Start --- //
    // 사용자가 충전할 금액
    private MutableLiveData<String> mPriceCharge = new MutableLiveData<>();

    // 선택된 카드
    private MutableLiveData<ResCardListDto.CardInformationDto> mSelectCardInfo =
            new MutableLiveData<>();
    // --- Field End --- //

    // --- Getter Start --- //
    public LiveData<String> getPriceCharge() {
        return mPriceCharge;
    }

    public LiveData<ResCardListDto.CardInformationDto> getSelectCardInfo() {
        return mSelectCardInfo;
    }
    // --- Getter End --- //

    // --- Setter Start --- //
    public void setPriceCharge(String priceCharge) {

        if (!priceCharge.replaceAll("원", "").equals(mPriceCharge.getValue())) {
            String price = priceCharge.replaceAll("[,원]", "");
            if (price.equals("")) {
                price = "0";
            } else {
                price = StringUtil.convertCommaPrice(price);
            }
            mPriceCharge.setValue(price);
        }
    }

    public void setSelectCardInfo(ResCardListDto.CardInformationDto selectCardInfo) {
        this.mSelectCardInfo.setValue(selectCardInfo);
    }
    // --- Setter End --- //
}
