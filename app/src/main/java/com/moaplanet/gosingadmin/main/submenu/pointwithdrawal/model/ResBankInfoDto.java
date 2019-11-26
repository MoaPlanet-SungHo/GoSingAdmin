package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

/**
 * 은행 정보 데이터 받을 dto
 */
public class ResBankInfoDto extends CommonResDto {

    // 은행 리스트를 저장할 맴버 변수
    @SerializedName("return_list")
    private List<BankInformationDto> mBankList;

    public List<BankInformationDto> getBankList() {
        return mBankList;
    }

    /**
     * 은행 정보 받을 Dto
     */
    public class BankInformationDto {

        // 은행 코드
        @SerializedName("back_cd")
        private String mBankCode;

        // 은행 이름
        @SerializedName("back_name")
        private String mBankName;

        public String getBankCode() {
            return mBankCode;
        }

        public String getBankName() {
            return mBankName;
        }
    }

}
