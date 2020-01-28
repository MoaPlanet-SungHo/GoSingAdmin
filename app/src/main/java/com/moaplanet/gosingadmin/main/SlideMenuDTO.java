package com.moaplanet.gosingadmin.main;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class SlideMenuDTO extends CommonResDto {

    @SerializedName("returnMap")
    private SlideMenuInfoModel slideMenuInfoModel;

    public SlideMenuInfoModel getSlideMenuInfoModel() {
        return slideMenuInfoModel;
    }

    public class SlideMenuInfoModel extends ResPointDto {

        // 가맹점 이름
        @SerializedName("shop_name")
        private String shopName;

        public String getShopName() {
            return shopName;
        }

    }
}
