package com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

/**
 * 출금 계좌 조회 데이터 받을 dto
 */
public class ResSearchDepositAccount extends CommonResDto {

    // 출금 계좌 정보
    @SerializedName("return_map")
    private DepositAccount mDepositAccount;

    public DepositAccount getDepositAccount() {
        return mDepositAccount;
    }

    public class DepositAccount {

        // 계좌 PK
        @SerializedName("vrcis_info_seq")
        private String bankAccountPk;

        // 은행 코드
        @SerializedName("back_cd")
        private String bankCd;

        // 은행 이름
        @SerializedName("back_name")
        private String bankName;

        // 계좌 번호
        @SerializedName("account_number")
        private String accountNumber;

        // 예금주
        @SerializedName("user_name")
        private String name;

        // 출금 가능 금액
        @SerializedName("with_possi_point")
        private int possibleWithdrawalPrice;

        // 출금 수수료
        @SerializedName("with_fees")
        private int withdrawalFee;

        public String getBankAccountPk() {
            return bankAccountPk;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getBankCd() {
            return bankCd;
        }

        public String getBankName() {
            return bankName;
        }

        public String getName() {
            return name;
        }

        public int getPossibleWithdrawalPrice() {
            return possibleWithdrawalPrice;
        }

        public int getWithdrawalFee() {
            return withdrawalFee;
        }
    }

}
