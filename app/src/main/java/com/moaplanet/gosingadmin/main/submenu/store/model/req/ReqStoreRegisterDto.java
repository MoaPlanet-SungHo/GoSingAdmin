package com.moaplanet.gosingadmin.main.submenu.store.model.req;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReqStoreRegisterDto {
    // 업소명
    @SerializedName("shop_nm")
    private String storeName;

    // 도로명 주소
    @SerializedName("road_name")
    private String roadAddress;

    // 상세주소
    @SerializedName("address")
    private String detailAddress;

    // 간편 주소
    @SerializedName("address_simple")
    private String simpleAddress;

    // 우편번호
    @SerializedName("post_num")
    private String postNumber;

    // 행정구역 코드
    @SerializedName("adm_cd")
    private String admCd;

    // 동 주소
    @SerializedName("dong")
    private String emdNm;

    // x좌표
    @SerializedName("ent_x")
    private String entX;

    // y좌표
    @SerializedName("ent_y")
    private String entY;

    // 사장님 전화번호
    @SerializedName("phone_number")
    private String bossTel;

    // 업소 전화번호
    @SerializedName("tel")
    private String storeTel;

    // 대표 사진
    @SerializedName("shop_photo")
    private Map<String, String> storePhoto = new HashMap<>();

    // 대표 삭제 사진
    @SerializedName("delete_photo")
    private List<String> removePhoto;

    // 룸정보
    @SerializedName("room_info")
    private List<RoomInfoDto> roomInfoDtoList;

    // 사장님 안내 멘트
    @SerializedName("ceo_noti")
    private String ceoComment;

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setSimpleAddress(String simpleAddress) {
        this.simpleAddress = simpleAddress;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public void setAdmCd(String admCd) {
        this.admCd = admCd;
    }

    public void setEmdNm(String emdNm) {
        this.emdNm = emdNm;
    }

    public void setEntX(String entX) {
        this.entX = entX;
    }

    public void setEntY(String entY) {
        this.entY = entY;
    }

    public void setStoreTel(String storeTel) {
        this.storeTel = storeTel;
    }

    public void setBossTel(String storeTel) {
        this.bossTel = bossTel;
    }

    public void setCeoComment(String ceoComment) {
        this.ceoComment = ceoComment;
    }

    public Map<String, String> getStorePhoto() {
        return storePhoto;
    }

    public void setRoomInfoDtoList(List<RoomInfoDto> roomInfoDtoList) {
        this.roomInfoDtoList = roomInfoDtoList;
    }

    public class RoomInfoDto {
        // 가격
        @SerializedName("price")
        private String price;

        // 룸 최대 인원
        @SerializedName("people_per_room")
        private int peoplePerRoom;

        // 룸 타입
        @SerializedName("room_type")
        private int roomType;

        // 룸 이름
        @SerializedName("room_name")
        private String roomName;

        // 전송 타입
        @SerializedName("t_type")
        private String sentType;

        public void setPrice(String price) {
            this.price = price;
        }

        public void setPeoplePerRoom(int peoplePerRoom) {
            this.peoplePerRoom = peoplePerRoom;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public void setRoomType(int roomType) {
            this.roomType = roomType;
        }

        public void setSentType(String sentType) {
            this.sentType = sentType;
        }
    }
}
