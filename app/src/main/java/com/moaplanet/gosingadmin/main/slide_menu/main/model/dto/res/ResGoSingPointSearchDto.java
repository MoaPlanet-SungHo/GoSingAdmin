package com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.common.model.dto.res.ResPointDto;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.Map;

/**
 * 고싱 포인트 정보를 받을 res dto
 */
public class ResGoSingPointSearchDto extends CommonResDto {

    // 포인트 정보를 받을 맴버변수
    @SerializedName("returnMap")
    private ResPointDto mPointDto;

    public ResPointDto getPointDto() {
        return mPointDto;
    }
}
