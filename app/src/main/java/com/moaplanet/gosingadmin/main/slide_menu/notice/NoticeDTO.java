package com.moaplanet.gosingadmin.main.slide_menu.notice;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

/**
 * 공지사항 DTO
 */
public class NoticeDTO extends CommonResDto {

    // 공지사항 url
    @SerializedName("notice_url")
    private String baseNoticeUrl;

    // 페이지 정보
    @SerializedName("getPager_info")
    private PageInfoModel pageInfoModel;

    // 공지사항 리스트
    @SerializedName("notice_list")
    private List<NoticeModel> noticeList;

    public List<NoticeModel> getNoticeList() {
        return noticeList;
    }

    public String getBaseNoticeUrl() {
        return baseNoticeUrl;
    }

    public PageInfoModel getPageInfoModel() {
        return pageInfoModel;
    }

    public class NoticeModel {

        // 공지사항 고유 값
        @SerializedName("envent_notice_seq")
        private String seq;

        // 타이틀
        @SerializedName("title")
        private String title;

        // 작성시간
        @SerializedName("reg_time")
        private String wrtieTime;

        // 수정 시간
        @SerializedName("modify_time")
        private String modifyTime;

        public String getTitle() {
            return title;
        }

        public String getSeq() {
            return seq;
        }

        /**
         * 공지사항 시간 반환
         */
        public String noticeDate() {
            if (modifyTime == null || modifyTime.length() == 0) {
                return wrtieTime;
            } else {
                return modifyTime;
            }
        }
    }

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
    }

}
