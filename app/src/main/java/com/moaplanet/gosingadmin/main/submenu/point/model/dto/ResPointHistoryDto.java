package com.moaplanet.gosingadmin.main.submenu.point.model.dto;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

/**
 * 포인트 리스트 데이터 받을 dto
 */
public class ResPointHistoryDto extends CommonResDto {

    @SerializedName("point_list")
    private List<PointHistoryDto> pointHistoryDtoList;

    public class PointHistoryDto {
        // pk
        @SerializedName("info_pk")
        private String infoPk;

        // 1.받다, 2.주다
        @SerializedName("info_pk_type")
        private int infoPkType;

        // 제목
        @SerializedName("title")
        private String title;

        // 등록 날짜
        @SerializedName("insert_date")
        private String insertDate;

        // 포인트 액수
        @SerializedName("point_info")
        private String pointInfo;

        // 1.무통장 2.카드
        @SerializedName("earn_type")
        private int earnType;

        // 매장이름
        @SerializedName("shop_nm")
        private String shopNm;

        // 유효기간 시작 날짜
        @SerializedName("start_date")
        private String startDate;

        // 유효기간 마감 날짜
        @SerializedName("end_date")
        private String endDate;

        // 종류 ex ) 1.적립금 지급, 2.포인트 충전, 3.포인트 출금, 4.포인트 결재 , 5.적립(사용자 결제), 6.이벤트 적립, 7.관리자 적립, 8.관리자 출금, 9.고객 결제
        @SerializedName("title_cd")
        private int titleCd;

        // 사유
        @SerializedName("content")
        private String content;

        public String getInfoPk() { return infoPk; }

        public int getInfoPkType() { return infoPkType; }

        public String getTitle() { return title; }

        public String getInsertDate() { return insertDate; }

        public String getPointInfo() { return pointInfo; }

        public int getEarnType() { return earnType; }

        public String getShopNm() { return shopNm; }

        public String getStartDate() { return startDate; }

        public String getEndDate() { return endDate; }

        public int getTitleCd() { return titleCd; }

        public String getContent() { return content; }
    }

    public List<PointHistoryDto> getPointHistoryDtoList() { return pointHistoryDtoList; }
}
