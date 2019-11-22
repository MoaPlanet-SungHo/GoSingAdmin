package com.moaplanet.gosingadmin.main.submenu.point.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.point.model.dto.ResPointHistoryDto;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.Arrays;

public class PointHistoryDialog extends DialogFragment {

    private View view;
    private String type;
    private View.OnClickListener onClickListener;

    private ResPointHistoryDto.PointHistoryDto pointModel;

//    public void setType(String type) {
//        this.type = type;
//    }

    public void setPointModel(ResPointHistoryDto.PointHistoryDto pointModel) {
        this.pointModel = pointModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_point_history, container);
        initView();
        return view;
    }

    private void initView() {

        setCancelable(false);

        PointType pointType = PointType.onFindType(pointModel.getTitleCd());

        if (pointType == null) {
            dismiss();
        } else {

            TextView dialogDone = view.findViewById(R.id.tv_point_history_dialog_done);
            dialogDone.setOnClickListener(onClickListener);

            // 플러스 마이너스 아이콘
            TextView dialogPoint = view.findViewById(R.id.tv_point_history_dialog_point);
            final int GET_POINT = 1;
            if (pointModel.getInfoPkType() == GET_POINT) {
                dialogPoint.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_4300ff_plus,
                        0,
                        0,
                        0);
            } else {
                dialogPoint.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_ff4a24_minus,
                        0,
                        0,
                        0);
            }

            // 포인트
            dialogPoint.setText(
                    getString(
                            R.string.common_price_won,
                            StringUtil.convertCommaPrice(pointModel.getPointInfo())));

            // 타이틀
            TextView title = view.findViewById(R.id.tv_dialog_point_history_title);
            title.setText(pointType.getTitle());

            View oneContentGroup = view.findViewById(R.id.ll_dialog_point_history_one_content_group);
            View twoContentGroup = view.findViewById(R.id.ll_dialog_point_history_two_content_group);
            View threeContentGroup = view.findViewById(R.id.ll_dialog_point_history_three_content_group);

            TextView oneContentTitle = view.findViewById(R.id.tv_dialog_point_history_one_content_title);

            if (pointType.getContentCount() == 1) {
                twoContentGroup.setVisibility(View.GONE);
                threeContentGroup.setVisibility(View.GONE);

                // 관리자 차감일경우
                final int ADMIN_DEDUCTION_TYPE = 8;
                if (pointType.getType() == ADMIN_DEDUCTION_TYPE) {
                    oneContentTitle.setText("사유");
                } else {
                    oneContentTitle.setText("일시");
                }

            } else if (pointType.getContentCount() == 2) {
                threeContentGroup.setVisibility(View.GONE);
            } else {
                // 3번째 컨텐츠 내용
                TextView threeContent = view.findViewById(R.id.tv_ll_dialog_point_history_three_content);
                threeContent.setText(getString(R.string.dialog_point_history_validity,
                        pointModel.getStartDate(),
                        pointModel.getEndDate()));
            }

            // 일시
//            if (pointType.getType() == ADMIN_DEDUCTION_TYPE) {
//                // 관리자 차감일때는 숨김
//                dateAndTime.setVisibility(View.GONE);
//            } else {
//
//            }
        }
    }

    public void setDialogDoneClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    enum PointType {
        RESERVE_PAYMENT("적립금 지금", 1, false, 2),
        CHARGE_POINT("포인트 충전", 2, true, 3),
        POINT_WITHDRAWAL("포인트 출금", 3, false, 1),
        POINT_PAYMENT("포인트 결제", 4, false, 2),
        EVENT_SAVE("이벤트 적립", 6, true, 3),
        RESERVE_ADMIN("관리자 지급", 7, true, 3),
        ADMIN_DEDUCTION("관리자 차감", 8, false, 1),
        CUSTOMER_PAYMENT("고객 결제", 9, true, 3);

        // 타이틀
        private String title;
        // 다이얼로그 종류
        private int type;
        // 플러스 표시유무
        private boolean isPlus;
        // 다이얼로그에 표시할 항목 개수
        private int contentCount;

        PointType(String title, int type, boolean isPlus, int contentCount) {
            this.title = title;
            this.type = type;
            this.isPlus = isPlus;
            this.contentCount = contentCount;
        }

        public String getTitle() {
            return title;
        }

        public int getType() {
            return type;
        }

        public int getContentCount() {
            return contentCount;
        }

        public static PointType onFindType(int type) {
            for (PointType pointType : PointType.values()) {
                if (pointType.getType() == type) {
                    return pointType;
                }
            }
            return null;
        }
    }
}
