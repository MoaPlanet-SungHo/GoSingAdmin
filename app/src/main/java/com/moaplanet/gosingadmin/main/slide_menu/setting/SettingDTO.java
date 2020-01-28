package com.moaplanet.gosingadmin.main.slide_menu.setting;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

/**
 * 설정 화면에 표시되는 정보
 */
public class SettingDTO extends CommonResDto {

    // 혜택 알림
    @SerializedName("gossing_benefits")
    private String benefit;

    // 야간 알림
    @SerializedName("nighttime_benefits")
    private String night;

    // 앱버전
    @SerializedName("app_version")
    private int appVersion;

    public String getBenefit() {
        return benefit;
    }

    public String getNight() {
        return night;
    }

    public int getAppVersion() {
        return appVersion;
    }
}
