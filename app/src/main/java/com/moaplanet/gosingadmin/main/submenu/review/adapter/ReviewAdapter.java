package com.moaplanet.gosingadmin.main.submenu.review.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.review.model.ResReviewDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.model.CommonResDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.DeviceUtil;
import com.moaplanet.gosingadmin.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

public class ReviewAdapter extends PagedListAdapter<ResReviewDTO.ReviewInfoModel, ReviewAdapter.ReviewHolder> {

    // 전체 리뷰, 댓글 없는 리뷰 둘다 새로고침
    public final int REVIEW_REFRESH_ALL = 0;
    // 현재 페이지의 리뷰만 새로고침
    public final int REVIEW_REFRESH_NOW = 1;

    private FragmentManager fm;

    public onRefreshCallback onRefreshCallback;
    public onNotSessionCallback onNotSessionCallback;

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ViewUtil.getHolderView(parent, R.layout.item_review);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.init(getItem(position), position);
    }

    public void setOnRefreshCallback(ReviewAdapter.onRefreshCallback onRefreshCallback) {
        this.onRefreshCallback = onRefreshCallback;
    }

    public void setOnNotSessionCallback(ReviewAdapter.onNotSessionCallback onNotSessionCallback) {
        this.onNotSessionCallback = onNotSessionCallback;
    }

    public ReviewAdapter(FragmentManager fm) {
        super(DIFF_CALLBACK);
        this.fm = fm;
    }

    private static DiffUtil.ItemCallback<ResReviewDTO.ReviewInfoModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<ResReviewDTO.ReviewInfoModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ResReviewDTO.ReviewInfoModel oldItem, @NonNull ResReviewDTO.ReviewInfoModel newItem) {
            return oldItem.getReviewPk().equals(newItem.getReviewPk());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ResReviewDTO.ReviewInfoModel oldItem, @NonNull ResReviewDTO.ReviewInfoModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    public class ReviewHolder extends RecyclerView.ViewHolder {

        // 이름
        private TextView mUserName;
        // 평점
        private MaterialRatingBar mRatingBar;
        // 작성일
        private TextView mDate;
        // 사장님 답글 작성 뷰
        private View mReplyGroup;
        // 답글 달기 버튼
        private Button mBtnReply;
        // 사장님 답글
        private EditText mEtReply;
        // 답글 취소 및 삭제
        private Button mBtnCancelOrRemove;
        // 답글 수정 및 추가
        private Button mBtnRegisterOrModify;
        // 답글 텍스트 길이
        private TextView mTvReplySize;
        // 리뷰 썸네일 그룹
        private View mReviewThumbnailGroup;
        private List<ImageView> mThumbnailList;
        // 답글 타입
        private int mReplyType;
        // 답글 등록
        private final int REPLY_REGISTER = 1;
        // 답글 수정 준비
        private final int REPLY_MODIFY_READY = 2;
        // 답글 수정
        private final int REPLY_MODIFY = 3;
        // 댓글 등록 다이얼로그
        private NoTitleDialog replyRegisterDialog;
        // 답글 취소 다이얼로그
        private NoTitleDialog replyCancelDialog;
        // 답글 삭제 다이얼로그
        private NoTitleDialog replyRemoveDialog;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            // 사용자 이름
            mUserName = itemView.findViewById(R.id.tv_item_review_user_name);
            // 평점
            mRatingBar = itemView.findViewById(R.id.rating_item_review);
            // 날짜
            mDate = itemView.findViewById(R.id.tv_item_review_write_date);
            // 답변 뷰
            mReplyGroup = itemView.findViewById(R.id.cl_item_review_ceo_reply_group);
            // 답변 달기 버튼
            mBtnReply = itemView.findViewById(R.id.btn_item_review_reply_comment);
            // 답변 입력 화면
            mEtReply = itemView.findViewById(R.id.et_item_review_reply_comment);
            // 삭제 및 취소 버튼
            mBtnCancelOrRemove = itemView.findViewById(R.id.btn_item_review_cancel_or_remove);
            // 등록 및 수정 버튼
            mBtnRegisterOrModify = itemView.findViewById(R.id.btn_item_review_register_or_modify);
            // 답변 글자수 표시
            mTvReplySize = itemView.findViewById(R.id.tv_item_review_reply_comment_count);
            // 리뷰 관련
            mReviewThumbnailGroup = itemView.findViewById(R.id.ll_item_review_image_group);

            mThumbnailList = new ArrayList<>();
            mThumbnailList.add(itemView.findViewById(R.id.iv_item_review_thumbnail_one));
            mThumbnailList.add(itemView.findViewById(R.id.iv_item_review_thumbnail_two));
            mThumbnailList.add(itemView.findViewById(R.id.iv_item_review_thumbnail_three));

            replyRegisterDialog = new NoTitleDialog();
            replyRegisterDialog.setUseYesOrNo(true);

            replyCancelDialog = new NoTitleDialog();
            replyCancelDialog.setUseYesOrNo(true);

            replyRemoveDialog = new NoTitleDialog();
            replyRemoveDialog.setUseYesOrNo(true);
        }

        private void init(ResReviewDTO.ReviewInfoModel model, int pos) {

            if (model != null) {
                mReplyType = REPLY_REGISTER;

                for (ImageView imageView : mThumbnailList) {
                    imageView.setImageResource(R.color.color_00000000);
                }

                // 이름 설정, 이름이 없을경우 핸드폰 번호로 표시
                if (model.getName() != null && !model.getName().equals("")) {
                    mUserName.setText(model.getName());
                } else {
                    mUserName.setText(model.getPhoneNumber());
                }

                // 평점 설정
                mRatingBar.setRating(model.getAvg());

                // 작성일
                mDate.setText(itemView.getContext().getString(
                        R.string.item_review_date_reg_date, model.getModifyDate()));

                // 답글 입력화면 공백으로 초기화
                mEtReply.setText("");

                // 답글 달려있는지 체크
                if (model.getReply() == null || model.getReply().equals("")) {
                    // 답글이 없을경우
                    mReplyGroup.setVisibility(View.GONE);
                    mBtnReply.setVisibility(View.VISIBLE);
                } else {
                    // 답글이 있을경우
                    mReplyType = REPLY_MODIFY_READY;
                    mReplyGroup.setVisibility(View.VISIBLE);
                    mBtnReply.setVisibility(View.GONE);

                    mEtReply.setText(model.getReply());
                    mTvReplySize.setText(itemView.getContext().getString(
                            R.string.item_review_reply_count, model.getReply().length()
                    ));

                    mBtnCancelOrRemove.setText(R.string.item_review_remove);
                    mBtnRegisterOrModify.setText(R.string.item_review_modify);
                    mEtReply.setEnabled(false);
                }

                //리뷰 썸네일 괄녀
                mReviewThumbnailGroup.setVisibility(View.GONE);
                if (model.getReviewImg() == null || model.getReviewImg().size() <= 0) {
                    mReviewThumbnailGroup.setVisibility(View.GONE);
                } else {
                    float thumbnailEmptySpace =
                            itemView.getContext().getResources()
                                    .getDimension(R.dimen.item_review_thumbnail_space);

                    int screenSize = DeviceUtil.getScreenWith(itemView.getContext());
                    int imgSize = (int) ((screenSize - thumbnailEmptySpace) / 3);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imgSize, imgSize);

                    int loopCount;
                    if (model.getReviewImg().size() > 3) {
                        loopCount = 3;
                    } else {
                        loopCount = model.getReviewImg().size();
                    }

                    for (int i = 0; i < loopCount; i++) {
                        mThumbnailList.get(i).setLayoutParams(layoutParams);
                        Glide.with(itemView.getContext())
                                .load(NetworkConstants.IMAGE_BASE_URL + model.getReviewImg().get(i))
                                .thumbnail(0.1f)
                                .placeholder(R.drawable.bg_store_thumbnail_loading)
                                .error(R.color.color_ffffff)
                                .into(mThumbnailList.get(i));
                    }

                    mReviewThumbnailGroup.setVisibility(View.VISIBLE);
                }


                // 댓글 달기 버튼 클릭
                mBtnReply.setOnClickListener(view -> {
                    mReplyType = REPLY_REGISTER;
                    mBtnCancelOrRemove.setText(R.string.item_review_cancel);
                    mBtnRegisterOrModify.setText(R.string.item_review_register);

                    mReplyGroup.setVisibility(View.VISIBLE);
                    mBtnReply.setVisibility(View.GONE);
                    mEtReply.setEnabled(true);
                });

                // 답글의 글자수 표시
                mEtReply.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        mTvReplySize.setText(itemView.getContext().getString(
                                R.string.item_review_reply_count, editable.length()
                        ));
                    }
                });

                // 등록 및 수정
                RxView.clicks(mBtnRegisterOrModify)
                        .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(click -> {

                            // 답변 공백 유무 체크
                            if (!TextUtils.isEmpty(mEtReply.getText().toString().trim())) {
                                if (mReplyType == REPLY_MODIFY_READY) {
                                    // 답글 수정 준비
                                    mBtnRegisterOrModify.setText(R.string.item_review_register);
                                    mBtnCancelOrRemove.setText(R.string.item_review_cancel);
                                    mEtReply.setEnabled(true);
                                    mEtReply.requestFocus();
                                    mReplyType = REPLY_MODIFY;

                                } else if (mReplyType == REPLY_MODIFY) {
                                    // 답글 수정
                                    replyRegisterDialog.setContent(R.string.item_review_modify_dialog_content);
                                    showReplyRegisterDialog(model, pos);
                                } else {
                                    // 답글 등록
                                    replyRegisterDialog.setContent(R.string.item_review_register_dialog_content);
                                    showReplyRegisterDialog(model, pos);
                                }
                            } else {
                                // 답변이 공백일 경우
                                Toast.makeText(itemView.getContext(),
                                        "사장님 댓글을 입력해 주세요.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        });

                // 답글 삭제 및 취소
                RxView.clicks(mBtnCancelOrRemove)
                        .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(click -> {

                            if (mReplyType == REPLY_MODIFY_READY) {
                                // 답글 삭제
                                showReplyRemoveDialog(model, pos);
                            } else {
                                showReplyCancelDialog(model);
                            }

                        });

            }

        }

        /**
         * 답글 삭제
         */
        private void showReplyRemoveDialog(ResReviewDTO.ReviewInfoModel model, int pos) {

            replyRemoveDialog.setContent("리뷰 댓글을 삭제하시겠습니까?");
            replyRemoveDialog.show(fm, "replyRemoveDialog");

            replyRemoveDialog.onNoOnClickListener(view -> replyRemoveDialog.dismiss());

            replyRemoveDialog.onDoneOnCliListener(view -> {
                replyRemoveDialog.dismiss();
                onReplyRemove(model, pos);
            });
        }

        /**
         * 답글 취소 다이얼로그 띄우기
         */
        private void showReplyCancelDialog(ResReviewDTO.ReviewInfoModel model) {
            if (mReplyType == REPLY_MODIFY) {
                replyCancelDialog.setContent("댓글 수정을 취소하시겠습니까?");
            } else {
                replyCancelDialog.setContent("댓글 등록을 취소하시겠습니까?");
            }
            replyCancelDialog.show(fm, "cancelDialog");

            replyCancelDialog.onNoOnClickListener(view -> replyCancelDialog.dismiss());

            replyCancelDialog.onDoneOnCliListener(view -> {

                if (mReplyType == REPLY_MODIFY) {
                    // 답글 수정 취소 - 이미 답글이 등록되어 있을경우
                    mEtReply.setEnabled(false);
                    mEtReply.setText(model.getReply());
                    mReplyType = REPLY_MODIFY_READY;
                    mBtnRegisterOrModify.setText(R.string.item_review_modify);
                    mBtnCancelOrRemove.setText(R.string.item_review_remove);
                    replyCancelDialog.dismiss();
                } else {
                    // 답글 취소 - 답글 첫 등록시
                    mEtReply.setText("");
                    mEtReply.setEnabled(false);
                    mReplyGroup.setVisibility(View.GONE);
                    mBtnReply.setVisibility(View.VISIBLE);
                    replyCancelDialog.dismiss();
                }
            });

        }

        /**
         * 답글 달기 다이얼로그 띄우가
         */
        private void showReplyRegisterDialog(ResReviewDTO.ReviewInfoModel model, int pos) {
            replyRegisterDialog.show(fm, "registerModifyDialog");
            replyRegisterDialog.onDoneOnCliListener(view -> {
                replyRegisterDialog.dismiss();
                onReplyModifyOrRegister(model, pos);
            });

            replyRegisterDialog.onNoOnClickListener(view -> {
                replyRegisterDialog.dismiss();
            });
        }

        /**
         * 답변 추가 및 수정
         */
        private void onReplyModifyOrRegister(ResReviewDTO.ReviewInfoModel model, int pos) {
            RetrofitService.getInstance().getGoSingApiService()
                    .onServerReviewModifyOrRegister(model.getReviewPk(), mEtReply.getText().toString())
                    .enqueue(new MoaAuthCallback<CommonResDto>(
                            RetrofitService.getInstance().getMoaAuthConfig(),
                            RetrofitService.getInstance().getSessionChecker()
                    ) {
                        @Override
                        public void onFinalResponse(Call<CommonResDto> call, CommonResDto resModel) {

                            if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {

                                if (mReplyType == REPLY_REGISTER) {
                                    // 댓글 등록일 경우
                                    Toast.makeText(itemView.getContext(),
                                            "등록한 댓글이 등록되었습니다."
                                            , Toast.LENGTH_SHORT).show();
                                    if (onRefreshCallback != null) {
                                        onRefreshCallback.onRefresh(REVIEW_REFRESH_ALL, pos);
                                    }
                                } else {
                                    // 수정일 경우
                                    Toast.makeText(itemView.getContext(),
                                            "수정한 댓글이 수정되었습니다."
                                            , Toast.LENGTH_SHORT).show();
                                    if (onRefreshCallback != null) {
                                        onRefreshCallback.onRefresh(REVIEW_REFRESH_NOW, pos);
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFinalFailure(Call<CommonResDto> call, boolean isSession, Throwable t) {
                            Toast.makeText(itemView.getContext(),
                                    "다시 시도해 주세요."
                                    , Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinalNotSession() {
                            super.onFinalNotSession();
                            if (onNotSessionCallback != null) {
                                onNotSessionCallback.onNotSession();
                            }
                        }
                    });
        }

        /**
         * 답변 삭제
         */
        private void onReplyRemove(ResReviewDTO.ReviewInfoModel model, int pos) {

            RetrofitService.getInstance().getGoSingApiService()
                    .onServerReviewRemove(model.getReviewPk())
                    .enqueue(new MoaAuthCallback<CommonResDto>(
                            RetrofitService.getInstance().getMoaAuthConfig(),
                            RetrofitService.getInstance().getSessionChecker()
                    ) {
                        @Override
                        public void onFinalResponse(Call<CommonResDto> call, CommonResDto resModel) {

                            if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                                if (onRefreshCallback != null) {
                                    onRefreshCallback.onRefresh(REVIEW_REFRESH_ALL, pos);
                                }
                                Toast.makeText(itemView.getContext(),
                                        "리뷰 댓글이 삭제되었습니다."
                                        , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(itemView.getContext(),
                                        "다시 시도해 주세요."
                                        , Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFinalFailure(Call<CommonResDto> call, boolean isSession, Throwable t) {
                            Toast.makeText(itemView.getContext(),
                                    "다시 시도해 주세요."
                                    , Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinalNotSession() {
                            super.onFinalNotSession();
                            if (onNotSessionCallback != null) {
                                onNotSessionCallback.onNotSession();
                            }
                        }
                    });
        }
    }

    public interface onRefreshCallback {
        void onRefresh(int type, int pos);
    }

    public interface onNotSessionCallback {
        void onNotSession();
    }

}
