package com.moaplanet.gosingadmin.main.qrpayment.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class ResCreateQrCodeDto extends CommonResDto {

    // QR코드 이미지 경로
    @SerializedName("qrcode")
    private String pathQrCode;

    // 가맹점 이름
    @SerializedName("shop_nm")
    private String storeName;

    // QRCode SEQ 값
    @SerializedName("shop_qr_seq")
    private String qrCodeSeq;

    public String getStoreName() {
        return storeName;
    }

    public String getPathQrCode() {
        return pathQrCode;
    }

    public String getQrCodeSeq() {
        return qrCodeSeq;
    }
}
