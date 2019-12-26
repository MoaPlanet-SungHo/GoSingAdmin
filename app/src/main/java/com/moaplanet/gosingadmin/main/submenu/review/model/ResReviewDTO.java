package com.moaplanet.gosingadmin.main.submenu.review.model;

import com.google.gson.annotations.SerializedName;
import com.moaplanet.gosingadmin.network.model.CommonResDto;

import java.util.List;

/**
 * 리뷰 리스트 데이터를 받을 DTO
 */
public class ResReviewDTO extends CommonResDto {

    // 가맹점 정보
    @SerializedName("review_title")
    private StoreInfoModel mStoreInfoModel;

    // 리뷰 리스트
    @SerializedName("shop_review_list")
    private List<ReviewInfoModel> mReviewList;

    public StoreInfoModel getStoreInfoModel() {
        return mStoreInfoModel;
    }

    public List<ReviewInfoModel> getReviewList() {
        return mReviewList;
    }

    /**
     * 가맹점 정보
     * 리뷰 리스트의 상단에 표시해줄 데이터들
     */
    public class StoreInfoModel {

        // 전체 리뷰 개수
        @SerializedName("all_review_count")
        private int mReviewTotalCount;

        // 등롣된 덧글 개수
        @SerializedName("all_comment_count")
        private int mReviewReplyCount;

        // 가맹점 평점
        @SerializedName("all_avg")
        private float mStoreAvg;

        public int getReviewTotalCount() {
            return mReviewTotalCount;
        }

        public int getReviewReplyCount() {
            return mReviewReplyCount;
        }

        public float getStoreAvg() {
            return mStoreAvg;
        }
    }

    /**
     * 리뷰 데이터
     */
    public class ReviewInfoModel {

        // 리뷰 pk
        @SerializedName("review_seq")
        private String mReviewPk;

        // 유저 핸드폰 번호
        @SerializedName("phone_number")
        private String mPhoneNumber;

        // 사용자 리뷰
        @SerializedName("review")
        private String mReview;

        // 리뷰 등록 날짜
        @SerializedName("reg_time")
        private String mRegDate;

        // 리뷰 수정 날짜
        @SerializedName("modify_time")
        private String mModifyDate;

        // 평점
        @SerializedName("star_count")
        private float mAvg;

        // 사장님 답글
        @SerializedName("shop_comment")
        private String mReply;

        // 리뷰 작성자 이름
        @SerializedName("user_nick_name")
        private String mName;

        // 리뷰 이미지
        @SerializedName("shop_review_img_lists")
        private List<String> mReviewImgList;

        public String getReviewPk() {
            return mReviewPk;
        }

        public String getPhoneNumber() {
            return mPhoneNumber;
        }

        public String getReview() {
            return mReview;
        }

        public String getRegDate() {
            return mRegDate;
        }

        public String getModifyDate() {
            return mModifyDate;
        }

        public float getAvg() {
            return mAvg;
        }

        public String getReply() {
            return mReply;
        }

        public String getName() {
            return mName;
        }

        public List<String> getReviewImg() {
            return mReviewImgList;
        }
    }
}
