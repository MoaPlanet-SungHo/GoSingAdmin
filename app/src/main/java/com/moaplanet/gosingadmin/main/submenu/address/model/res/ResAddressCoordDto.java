package com.moaplanet.gosingadmin.main.submenu.address.model.res;


import com.google.gson.annotations.SerializedName;

public class ResAddressCoordDto {

    @SerializedName("results")
    private ResultCoordDto resultCoordDto;

    public class ResultCoordDto {
        @SerializedName("common")
        private CommonAddressCoordDto commonAddressCoordDto;

        @SerializedName("juso")
        private AddressCoordInfoDto addressCoordInfoDto;
    }

    public class CommonAddressCoordDto {
        @SerializedName("errorCode")
        private String errorCode;
    }

    public class AddressCoordInfoDto {
        @SerializedName("entX")
        private String entX;
        @SerializedName("entY")
        private String entY;
    }

    public AddressCoordInfoDto getAddressCoordInfoDto() {
        return resultCoordDto.addressCoordInfoDto;
    }

}
