package com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.Map;

public class ResGoSingPointSearchDto extends CommonResDto {

    @SerializedName("returnMap")
    private Map<String, Integer> pointMap;

    public Map<String, Integer> getPointMap() {
        return pointMap;
    }
}
