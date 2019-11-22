package com.moaplanet.gosingadmin.intro;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

/**
 * 앱 버전을 받을 DTO
 */
public class ResVersionDTO extends CommonResDto {

    // 버전 정보
    @SerializedName("returnMap")
    private AppVersionModel appVersionModel;

    public AppVersionModel getAppVersionModel() {
        return appVersionModel;
    }

    public class AppVersionModel {

        // 최소버전
        @SerializedName("min_version")
        private int versionMin;

        // 최대 버전
        @SerializedName("max_version")
        private int versionMax;

        public int getVersionMin() {
            return versionMin;
        }

        public int getVersionMax() {
            return versionMax;
        }
    }

}
