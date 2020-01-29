package com.moaplanet.gosingadmin.main.slide_menu.event;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 이벤트 DTO
 */
public class EventDTO extends CommonResDto {

    // 페이지 정보
    @SerializedName("getPager_info")
    private PageInfoModel pageInfoModel;

    // 이벤트 정보
    @SerializedName("event_notice_list")
    private List<EventModel> eventList;

    // 이벤트 페이지 이동 url
    @SerializedName("notice_url")
    private String eventUrl;

    public String getEventUrl() {
        return eventUrl;
    }

    public PageInfoModel getPageInfoModel() {
        return pageInfoModel;
    }

    public List<EventModel> getEventList() {
        return eventList;
    }

    /**
     * 페이지 정보
     */
    public class PageInfoModel {

        // 현재 페이지
        @SerializedName("currentPage")
        private int currentPage;

        // 전체 페이지
        @SerializedName("totalPage")
        private int totalPage;

        // 전체 개수
        @SerializedName("totalCount")
        private int totalCount;

        public int getTotalPage() {
            return totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }
    }

    /**
     * 이벤트 정보
     */
    public class EventModel {

        // 이벤트 고유 값
        @SerializedName("event_notice_seq")
        private String eventSeq;

        // 타이틀
        @SerializedName("title")
        private String title;

        // 이벤트 시작 시간
        @SerializedName("prd_start_dt")
        private String startDate;

        // 이벤트 종료 시간
        @SerializedName("prd_end_dt")
        private String endDate;

        public String getEventSeq() {
            return eventSeq;
        }

        public String getTitle() {
            return title;
        }

        public String getEventDate() {
            String startDate = changeDateFormat(this.startDate);
            String endDate = changeDateFormat(this.endDate);
            return startDate + " ~ " + endDate;
        }

        private String changeDateFormat(String date) {

            // 서버에서 보내준느 형식
            SimpleDateFormat firstFormat = new SimpleDateFormat("yyyymmdd",
                    Locale.getDefault());

            Date changeDate = null;

            try {
                changeDate = firstFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (changeDate == null) {
                return "";
            } else {
                // 변경할 형식
                SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy.mm.dd",
                        Locale.getDefault());
                return afterFormat.format(changeDate);
            }

        }

    }

}
