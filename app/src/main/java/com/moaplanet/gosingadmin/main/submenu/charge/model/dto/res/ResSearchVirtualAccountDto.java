package com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class ResSearchVirtualAccountDto extends CommonResDto {

    // --- Field Start --- //

    // 가상계좌 정보를 담을 맴버 변수
    @SerializedName("returnMap")
    private VirtualAccountDto mVirtualAccountDto;

    // --- Field End --- //

    // --- Getter Start --- //

    public VirtualAccountDto getVirtualAccountDto() {
        return mVirtualAccountDto;
    }

    // --- Getter End --- //

    /**
     * 가상계좌 정보 조회할 모델
     */
    public class VirtualAccountDto {

        // --- Field Start --- //

        // 가상계좌 pk
        @SerializedName("vrcis_info_seq")
        private String mVirtaulAccountPk;

        // 은행 코드
        @SerializedName("bank_cd")
        private String mBankCd;

        // 은행 이름
        @SerializedName("bank_nm")
        private String mBankName;

        //가상계좌 번호
        @SerializedName("vrcis_number")
        private String mVirtaulAccountNumber;

        //가상계좌 번호
        @SerializedName("user_nm")
        private String mUserName;

        // --- Field End --- //

        // -- Getter Start --- //

        public String getVirtaulAccountPk() {
            return mVirtaulAccountPk;
        }

        public String getBankCd() {
            return mBankCd;
        }

        public String getBankName() {
            return mBankName;
        }

        public String getVirtaulAccountNumber() {
            return mVirtaulAccountNumber;
        }

        public String getUserName() {
            return mUserName;
        }

        // -- Getter Start --- //
    }
}
