package com.moaplanet.gosingadmin.main.qrpayment.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

/**
 * Created by jiwun on 2019-11-12.
 */
public class ResPaymentInitDto extends CommonResDto {

    @SerializedName("returnMap")
    private PaymentInitDto paymentInitDto;

    public class PaymentInitDto {
        // QR코드 pk
        @SerializedName("shop_qr_save_history_seq")
        private String qrCodePk;

        // 적립률
        @SerializedName("acrl_rate")
        private int reserveRatio;

        // 포인트
        @SerializedName("gossing_point")
        private int gossingPoint;

        public String getQrCodePk() {
            return qrCodePk;
        }

        public int getReserveRatio() {
            return reserveRatio;
        }

        public int getGossingPoint() {
            return gossingPoint;
        }
    }

    public PaymentInitDto getPaymentInitDto() {
        return paymentInitDto;
    }
}
