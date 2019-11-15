package com.moaplanet.gosingadmin.common.model.dto.res;

import com.google.gson.annotations.SerializedName;

/**
 * 포인트 정보를 담을 Dto
 */
public class ResPointDto {

    // 고싱포인트
    @SerializedName("gossing_point")
    private int mPoint;

    // 활성 예정 포인트
    @SerializedName("actv_schdl_point")
    private int mPointExpectedActive;

    public int getPoint() {
        return mPoint;
    }

    public int getPointExpectedActive() {
        return mPointExpectedActive;
    }

}
