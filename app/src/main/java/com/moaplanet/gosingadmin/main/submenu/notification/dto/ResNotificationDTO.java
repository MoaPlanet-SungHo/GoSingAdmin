package com.moaplanet.gosingadmin.main.submenu.notification.dto;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

/**
 * 알림 정보를 받을 res dto
 */
public class ResNotificationDTO extends CommonResDto {

    @SerializedName("alert_list")
    private List<NotificationModel> notificationDtoList;

    public class NotificationModel {
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

    public List<NotificationModel> getNotificationDtoList() {
        return notificationDtoList;
    }
}
