package com.moaplanet.gosingadmin.main.slide_menu.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.BuildConfig;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.main.qrpayment.activity.QrCodeActivity;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.MainViewModel;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResGoSingPointSearchDto;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.ChargeActivity;
import com.moaplanet.gosingadmin.main.submenu.non_member.activity.NonMemberSaveActivity;
import com.moaplanet.gosingadmin.main.submenu.notification.NotificationActivity;
import com.moaplanet.gosingadmin.main.submenu.point.activity.PointHistoryActivity;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.PointWithDrawalActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.ModifyStoreActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

public class MainFragment extends Fragment {

    private View view;
    private View btnPointHistory, btnNotification, btnAd, btnStore,
            btnReview, btnFoodOrder, btnNonmemberSave;
    private View slideMenu;
    private Button btnQrCode, btnCargePoint, btnWithdrawal;
    private MainViewModel mainViewModel;

    private TextView tvPoint, tvExpectedActivePoint;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(inflater, container);
        initListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPointInfo();
    }

    private void getPointInfo() {
        RetrofitService.getInstance().getGoSingApiService().onServerGoSingPoint()
                .enqueue(new MoaAuthCallback<ResGoSingPointSearchDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResGoSingPointSearchDto> call, ResGoSingPointSearchDto resModel) {
                        if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
                            if (resModel.getDetailCode() == 200) {
                                mainViewModel.setPointMap(resModel.getPointMap());
                                return;
                            }
                        }
                        errMsg();
                    }

                    @Override
                    public void onFinalFailure(Call<ResGoSingPointSearchDto> call, boolean isSession, Throwable t) {
                        errMsg();
                    }
                });
    }

    private void errMsg() {
        Toast.makeText(view.getContext(), "서버 통신에러 입니다.", Toast.LENGTH_SHORT).show();
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

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
        btnQrCode = view.findViewById(R.id.btn_fragment_main_qr_code);
        btnCargePoint = view.findViewById(R.id.btn_main_charge_point);
        btnWithdrawal = view.findViewById(R.id.btn_main_withdrawal_point);

        tvPoint = view.findViewById(R.id.tv_main_point);
        tvExpectedActivePoint = view.findViewById(R.id.tv_main_schedule_point);
    }

    private void initListener() {

        //잔액 포인트
        mainViewModel.getPointGoSing().observe(this, point ->
                tvPoint.setText(getString(
                        R.string.fragment_main_default_point,
                        point)));

        //활성 예정 포인트
        mainViewModel.getPointExpectedActive().observe(this, point ->
                tvExpectedActivePoint.setText(getString(
                        R.string.fragment_main_expected_active_point,
                        point)));

        //공지사항 (상단 우측)
        btnNotification.setOnClickListener(view -> moveActivity(NotificationActivity.class));

        //충전하기
        btnCargePoint.setOnClickListener(view -> moveActivity(ChargeActivity.class));

        //출금하기
        btnWithdrawal.setOnClickListener(view -> moveActivity(PointWithDrawalActivity.class));
//        btnWithdrawal.setOnClickListener(view -> onServiceReady());

        //업소관리
        btnStore.setOnClickListener(view -> moveActivity(ModifyStoreActivity.class));
//        btnStore.setOnClickListener(view -> onServiceReady());

        //리뷰관리
//        btnReview.setOnClickListener(view -> moveActivity(ReviewManagerActivity.class));
        btnReview.setOnClickListener(view -> onServiceReady());

        //포인트내역
        btnPointHistory.setOnClickListener(view -> moveActivity(PointHistoryActivity.class));
//        btnPointHistory.setOnClickListener(view -> onServiceReady());

        //광고관리
//        btnAd.setOnClickListener(view -> moveActivity(GoSingAdActivity.class));
        btnAd.setOnClickListener(view -> onServiceReady());

        //먹거리주문
//        btnFoodOrder.setOnClickListener(view -> moveActivity(FoodOrderActivity.class));
        btnFoodOrder.setOnClickListener(view -> onServiceReady());

        //비회원적립
        btnNonmemberSave.setOnClickListener(view -> moveActivity(NonMemberSaveActivity.class));

        //슬라이드 메뉴
        slideMenu.setOnClickListener(view -> {
            onServiceReady();
        });

        btnQrCode.setOnClickListener(view -> moveActivity(QrCodeActivity.class));


    }

    private void moveActivity(Class moveClass) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Intent intent = new Intent(view.getContext(), moveClass);
            startActivity(intent);
        } else {
            onServiceReady();
        }
    }

    private void onServiceReady() {
        Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
    }

}
