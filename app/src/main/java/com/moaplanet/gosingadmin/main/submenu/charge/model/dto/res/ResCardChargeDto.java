package com.moaplanet.gosingadmin.main.submenu.charge.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

/**
 * 카드로 충전 했을때 데이터 받을 Dto
 */
public class ResCardChargeDto extends CommonResDto {

    @SerializedName("point_return_map")
    private PointDto mPointDto;

    public PointDto getPointDto() {
        return mPointDto;
    }

    /**
     * 포인트 관련 데이터 담는 dto
     */
    public class PointDto {

        // 고씽 포인트
        @SerializedName("gossing_point")
        private int mPointGoSing;

        // 활성 예정 포인트
        @SerializedName("actv_schdl_point")
        private int mPointExpectedActive;

        public int getPointGoSing() {
            return mPointGoSing;
        }

        public int getPointExpectedActive() {
            return mPointExpectedActive;
        }
    }

}
