package com.moaplanet.gosingadmin.main.qrpayment.dto.req;

public class ReqCreateQrCodeDto {

    // QR코드 pk
    private String qrCodePk;

    // 적립 가격
    private String reservePrice;

    // 비적립가격
    private String noReservePrice;

    public void setQrCodePk(String qrCodePk) {
        this.qrCodePk = qrCodePk;
    }

    public void setReservePrice(String reservePrice) {
        this.reservePrice = removeComma(reservePrice);
    }

    public void setNoReservePrice(String noReservePrice) {
        this.noReservePrice = removeComma(noReservePrice);
    }

    public String getQrCodePk() {
        return qrCodePk;
    }

    public String getReservePrice() {
        return reservePrice;
    }

    public String getNoReservePrice() {
        return noReservePrice;
    }

    /**
     * 콤마 제거 해서 반환
     *
     * @param price 제거할 가격
     */
    private String removeComma(String price) {
        if (price == null) {
            return "0";
        } else {
            return price.replace(",", "");
        }
    }
}
