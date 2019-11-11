package com.moaplanet.gosingadmin.main.submenu.notification.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

/**
 * Created by jiwun on 2019-11-11.
 */
public class ResNotificationDto extends CommonResDto {

    @SerializedName("alert_list")
    private List<NotificationDto> notificationDtoList;

    public class NotificationDto {
        // 알림 pk
        @SerializedName("shop_alam_log_seq")
        private int notiPk;

        // 알림 타입
        @SerializedName("alam_type")
        private String notiType;

        // 알림 내용
        @SerializedName("content")
        private String notiContent;

        // 시간
        @SerializedName("reg_time")
        private String regTime;

        public int getNotiPk() {
            return notiPk;
        }

        public String getNotiContent() {
            return notiContent;
        }

        public String getNotiType() {
            return notiType;
        }

        public String getRegTime() {
            return regTime;
        }
    }

    public List<NotificationDto> getNotificationDtoList() {
        return notificationDtoList;
    }
}
