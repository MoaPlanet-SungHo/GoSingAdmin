package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class ResDepositablePointDto extends CommonResDto {
    @SerializedName("point_return_map")
    ResPointDto resPointDto;

    public ResPointDto getResPointDto() {
        return resPointDto;
    }
}
