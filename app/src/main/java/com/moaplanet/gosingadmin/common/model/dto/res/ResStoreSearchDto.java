package com.moaplanet.gosingadmin.common.model.dto.res;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

public class ResStoreSearchDto extends CommonResDto {

    @SerializedName("shop_info")
    private ShopInfoDto shopInfoDto;

    @SerializedName("photo_list")
    private List<ShopPhotoDto> shopPhotoDtoList;

    @SerializedName("room_info_list")
    private List<ShopRoomInfoDto> shopRoomInfoDtoList;

    public class ShopPhotoDto {
        // shop_info_img_seq
        @SerializedName("shop_info_img_seq")
        private String imgPk;

        @SerializedName("img_src")
        private String imgSrc;

        public String getImgPk() {
            return imgPk;
        }

        public String getImgSrc() {
            return imgSrc;
        }

    }

    public class ShopRoomInfoDto {
        @SerializedName("room_info_seq")
        private String seq;

        @SerializedName("room_type")
        private int roomType;

        @SerializedName("price")
        private String price;

        @SerializedName("people_per_room")
        private String perRoom;

        public String getPrice() {
            return price;
        }

        public String getPerRoom() {
            return perRoom;
        }

        public int getRoomType() {
            return roomType;
        }

        public String getSeq() {
            return seq;
        }
    }

    public class ShopInfoDto {
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

        // 업소 전화번호
        @SerializedName("tel")
        private String storeTel;

        // 사장님 안내 멘트
        @SerializedName("ceo_noti")
        private String ceoComment;

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
        private String phoneNumber;

        public String getEmdNm() {
            return emdNm;
        }

        public String getEntY() {
            return entY;
        }

        public String getEntX() {
            return entX;
        }

        public String getAdmCd() {
            return admCd;
        }

        public String getRoadAddress() {
            return roadAddress;
        }

        public String getCeoComment() {
            return ceoComment;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public String getPostNumber() {
            return postNumber;
        }

        public String getSimpleAddress() {
            return simpleAddress;
        }

        public String getStoreName() {
            return storeName;
        }

        public String getStoreTel() {
            return storeTel;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

    public ShopInfoDto getShopInfoDto() {
        return shopInfoDto;
    }

    public List<ShopRoomInfoDto> getShopRoomInfoDtoList() {
        return shopRoomInfoDtoList;
    }

    public List<ShopPhotoDto> getShopPhotoDtoList() {
        return shopPhotoDtoList;
    }
}
