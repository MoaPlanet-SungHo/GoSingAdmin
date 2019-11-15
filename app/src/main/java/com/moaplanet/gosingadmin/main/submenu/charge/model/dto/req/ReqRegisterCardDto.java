package com.moaplanet.gosingadmin.main.submenu.charge.model.dto.req;

/**
 * 카드 등록시 서버에 보낼 Req Dto
 */
public class ReqRegisterCardDto {

    // 카드 번호1
    private String mCardNumberOne;

    // 카드 번호2
    private String mCardNumberTwo;

    // 카드 번호3
    private String mCardNumberThree;

    // 카드 번호4
    private String mCardNumberFour;

    // 카드 년도
    private String mYear;

    // 카드 달
    private String mMonth;

    // 카드 비밀번호
    private String mPw;

    // 카드 생년월일 또는 사업자 번호
    private String mBirth;

    // 카드 이름
    private String mCardName;

    // --- Getter Start --- //

    public String getCardNumberOne() {
        return mCardNumberOne;
    }

    public String getCardNumberTwo() {
        return mCardNumberTwo;
    }

    public String getCardNumberThree() {
        return mCardNumberThree;
    }

    public String getCardNumberFour() {
        return mCardNumberFour;
    }

    public String getYear() {
        return mYear;
    }

    public String getMonth() {
        return mMonth;
    }

    public String getPw() {
        return mPw;
    }

    public String getBirth() {
        return mBirth;
    }

    public String getCardName() {
        return mCardName;
    }

    // --- Getter End --- //

    // --- Setter Start --- //

    public void setCardNumberOne(String mCardNumberOne) {
        this.mCardNumberOne = mCardNumberOne;
    }

    public void setCardNumberTwo(String mCardNumberTwo) {
        this.mCardNumberTwo = mCardNumberTwo;
    }

    public void setCardNumberThree(String mCardNumberThree) {
        this.mCardNumberThree = mCardNumberThree;
    }

    public void setCardNumberFour(String mCardNumberFour) {
        this.mCardNumberFour = mCardNumberFour;
    }

    public void setYear(String mYear) {
        this.mYear = mYear;
    }

    public void setMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public void setPw(String mPw) {
        this.mPw = mPw;
    }

    public void setBirth(String mBirth) {
        this.mBirth = mBirth;
    }

    public void setCardName(String mCardName) {
        this.mCardName = mCardName;
    }

    // --- Setter End --- //

}
