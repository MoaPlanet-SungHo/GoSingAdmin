package com.moaplanet.gosingadmin.main.submenu.address.model.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResAddressSearchDto {

    @SerializedName("results")
    private AddressDto addressDto;

    public class AddressCommonDto {
        // 페이지 번호
        @SerializedName("currentPage")
        private int currentPage;

        // 오류 코드
        @SerializedName("errorCode")
        private String errorCode;

        // 총 검색수
        @SerializedName("totalCount")
        private String totalCount;
    }

    public class AddressInfoDto {
        // 도로명 주소
        @SerializedName("roadAddr")
        private String roadAddress;

        // 지번 주소
        @SerializedName("jibunAddr")
        private String jibunAddress;

        // 우편번호
        @SerializedName("zipNo")
        private String zipNo;

        // 행정구역 코드
        @SerializedName("admCd")
        private String admCd;

        // 읍면동명
        @SerializedName("emdNm")
        private String emdNm;

        public String getZipNo() {
            return zipNo;
        }

        public String getRoadAddress() {
            return roadAddress;
        }

        public String getJibunAddress() {
            return jibunAddress;
        }
    }

    public List<AddressInfoDto> getAddressInfoDtoList() {
        return addressDto.addressInfoDtoList;
    }

    public class AddressDto {
        @SerializedName("common")
        private AddressCommonDto addressCommonDto;

        @SerializedName("juso")
        private List<AddressInfoDto> addressInfoDtoList;
    }
}
