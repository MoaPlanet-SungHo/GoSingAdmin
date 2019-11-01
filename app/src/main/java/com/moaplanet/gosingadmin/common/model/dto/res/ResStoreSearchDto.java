package com.moaplanet.gosingadmin.common.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class ResStoreSearchDto extends CommonResDto {

    @SerializedName("shop_info")
    private ShopInfoDto shopInfoDto;


    private class ShopInfoDto {
        @SerializedName("shop_nm")
        private String ShopName;

        @SerializedName("road_name")
        private String roadName;

        @SerializedName("address")
        private String address;
    }

}
