package com.moaplanet.gosingadmin.main.submenu.non_member.model;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

public class ResNonMemberPointSaveInitDTO extends CommonResDto {

    @SerializedName("returnMap")
    private ResPointDto pointDto;

    public ResPointDto getPointDto() {
        return pointDto;
    }
}
