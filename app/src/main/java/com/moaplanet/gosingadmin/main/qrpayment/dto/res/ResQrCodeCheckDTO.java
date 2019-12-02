package com.moaplanet.gosingadmin.main.qrpayment.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

/**
 * qr 코드 체크 dto
 */
public class ResQrCodeCheckDTO extends CommonResDto {

    @SerializedName("isConfirm")
    private boolean isPayment;

    public boolean getIsPayment() {
        return isPayment;
    }

}
