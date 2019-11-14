package com.moaplanet.gosingadmin.main.submenu.charge.model.dto.req;

/**
 * 카드 충전시 req Dto
 */
public class ReqCardChargeDto {

    // -- Field Start-- //
    // 카드 해쉬 pk
    private String mCardHashPk;

    // 충전할 금액
    private String mChargeMoney;
    // -- Field End-- //

    // -- Getter Start -- //
    public String getCardHashPk() {
        return mCardHashPk;
    }

    public String getChargeMoney() {
        return mChargeMoney;
    }
    // -- Getter Start -- //

    // -- Setter Start -- //
    public void setCardHashPk(String cardHashPk) {
        this.mCardHashPk = cardHashPk;
    }

    public void setChargeMoney(String chargeMoney) {
        this.mChargeMoney = chargeMoney;
    }
    // -- Setter Start -- //

}
