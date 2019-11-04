package com.moaplanet.gosingadmin.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.submenu.review.activity.ReviewManagerActivity;

public class MainFragment extends Fragment {

    private View view;
    private View btnPointHistory, btnNotification, btnAd, btnStore,
            btnReview, btnFoodOrder, btnNonmemberSave;
    private View slideMenu;
    private Button mBtnMakeQrCode, btnCargePoint, btnWithdrawal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(inflater, container);
        initListener();
        return view;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        LinearLayout noticeView = view.findViewById(R.id.ll_main_notice_group);
        for (int i = 0; i < 3; i++) {
            View noticeContent = inflater.inflate(R.layout.item_main_notice_top_three, container, false);
            noticeView.addView(noticeContent);
        }
        noticeView.setOnClickListener(view -> onServiceReady());

        btnPointHistory = view.findViewById(R.id.cl_main_point_history);
        btnNotification = view.findViewById(R.id.cl_main_notification);
        btnAd = view.findViewById(R.id.cl_main_ad_management);
        slideMenu = view.findViewById(R.id.cl_main_slide_menu);
        btnStore = view.findViewById(R.id.cl_main_store_management);
        btnReview = view.findViewById(R.id.cl_main_review_management);
        btnFoodOrder = view.findViewById(R.id.cl_main_food_order);
        btnNonmemberSave = view.findViewById(R.id.cl_main_non_member_save);
        mBtnMakeQrCode = view.findViewById(R.id.payment_qrcode_product);
        btnCargePoint = view.findViewById(R.id.btn_main_charge_point);
        btnWithdrawal = view.findViewById(R.id.btn_main_withdrawal_point);

    }

    private void initListener() {
//        btnPointHistory.setOnClickListener(view -> moveActivity(PointHistoryActivity.class));
        btnPointHistory.setOnClickListener(view -> onServiceReady());
//        btnNotification.setOnClickListener(view -> moveActivity(NotificationActivity.class));
        btnNotification.setOnClickListener(view -> onServiceReady());
//        btnAd.setOnClickListener(view -> moveActivity(GoSingAdActivity.class));
        btnAd.setOnClickListener(view -> onServiceReady());
//        btnStore.setOnClickListener(view -> moveActivity(ModifyStoreActivity.class));
        btnStore.setOnClickListener(view -> onServiceReady());
        btnReview.setOnClickListener(view -> moveActivity(ReviewManagerActivity.class));
//        btnFoodOrder.setOnClickListener(view -> moveActivity(FoodOrderActivity.class));
        btnFoodOrder.setOnClickListener(view -> onServiceReady());
//        btnNonmemberSave.setOnClickListener(view -> moveActivity(NonMemberSaveActivity.class));
        btnNonmemberSave.setOnClickListener(view -> onServiceReady());
        slideMenu.setOnClickListener(view -> {
            onServiceReady();
//            if (getActivity() != null && getActivity() instanceof MainActivity) {
//                ((MainActivity) getActivity()).openNavigation();
//            }
        });
//        mBtnMakeQrCode.setOnClickListener(view -> moveActivity(PaymentActivity.class));
        mBtnMakeQrCode.setOnClickListener(view -> onServiceReady());
//        btnCargePoint.setOnClickListener(view -> moveActivity(ChargeActivity.class));
        btnCargePoint.setOnClickListener(view -> onServiceReady());
//        btnWithdrawal.setOnClickListener(view -> moveActivity(PointWithDrawalActivity.class));
        btnWithdrawal.setOnClickListener(view -> onServiceReady());

    }

    private void moveActivity(Class moveClass) {
        Intent intent = new Intent(view.getContext(), moveClass);
        startActivity(intent);
    }

    private void onServiceReady() {
        Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
    }
}
