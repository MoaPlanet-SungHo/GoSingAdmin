package com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

/**
 * 카드 리스트 데이터 받을 Dto
 */
public class ResCardListDto extends CommonResDto {

    // 카드 리스트
    @SerializedName("card_list")
    private List<CardInformationDto> mCardInformationDtoList;

    // --- getter start --- //

    public List<CardInformationDto> getCardInformationDtoList() {
        return mCardInformationDtoList;
    }


    // --- getter end --- //

    /**
     * 카드 정보를 담는 Dto
     */
    public class CardInformationDto {

        // 카드 헤쉬
        @SerializedName("card_hash")
        private String mCardHash;

        // 카드 이름
        @SerializedName("card_name")
        private String mCardName;

        // 배치키
        @SerializedName("batch_key")
        private String mBatchKey;

        // ---- getter start ----- //
        public String getCardHash() {
            return mCardHash;
        }

        public String getCardName() {
            return mCardName;
        }

        public String getBatchKey() {
            return mBatchKey;
        }
        // ---- getter end ----- //
    }

}
