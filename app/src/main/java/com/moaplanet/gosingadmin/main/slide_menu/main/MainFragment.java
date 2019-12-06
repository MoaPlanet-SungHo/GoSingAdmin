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

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.main.IntroActivity;
import com.moaplanet.gosingadmin.main.qrpayment.activity.QrCodeActivity;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.MainViewModel;
import com.moaplanet.gosingadmin.main.slide_menu.main.model.dto.res.ResGoSingPointSearchDto;
import com.moaplanet.gosingadmin.main.submenu.ad.activity.GoSingAdActivity;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.ChargeActivity;
import com.moaplanet.gosingadmin.main.submenu.food.activity.FoodOrderActivity;
import com.moaplanet.gosingadmin.main.submenu.non_member.activity.NonMemberSaveActivity;
import com.moaplanet.gosingadmin.main.submenu.notification.NotificationActivity;
import com.moaplanet.gosingadmin.main.submenu.point.activity.PointHistoryActivity;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.activity.PointWithDrawalActivity;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.activity.RegisterAccountActivity;
import com.moaplanet.gosingadmin.main.submenu.review.activity.ReviewManagerActivity;
import com.moaplanet.gosingadmin.main.submenu.store.activity.ModifyStoreActivity;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

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
                                mainViewModel.setPoint(resModel.getPointDto());
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

    @Override
    public void onResume() {
        super.onResume();
        getPointInfo();
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

        RxView.clicks(noticeView)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onServiceReady());

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
        RxView.clicks(btnNotification)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivityWidthDebug(NotificationActivity.class));

        //충전하기
        RxView.clicks(btnCargePoint)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(ChargeActivity.class));

        //출금하기
        RxView.clicks(btnWithdrawal)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> mainViewModel.onSearchDepositAccount());

        //업소관리
        RxView.clicks(btnStore)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(ModifyStoreActivity.class));

        //리뷰관리
        RxView.clicks(btnReview)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivityWidthDebug(ReviewManagerActivity.class));

        //포인트내역
        RxView.clicks(btnPointHistory)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(PointHistoryActivity.class));

        //광고관리
        RxView.clicks(btnAd)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivityWidthDebug(GoSingAdActivity.class));

        //먹거리주문
        RxView.clicks(btnFoodOrder)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivityWidthDebug(FoodOrderActivity.class));

        //비회원적립
        RxView.clicks(btnNonmemberSave)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(NonMemberSaveActivity.class));

        //슬라이드 메뉴
        RxView.clicks(slideMenu)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onServiceReady());

        RxView.clicks(btnQrCode)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> moveActivity(QrCodeActivity.class));

        // 세션 없을경우
        mainViewModel.getSession().observe(this, isSession -> {
            if (!isSession) {
                Toast.makeText(view.getContext(),
                        R.string.common_not_exist_session,
                        Toast.LENGTH_SHORT)
                        .show();

                if (getActivity() != null) {
                    Intent intent = new Intent(view.getContext(), IntroActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(
                            GoSingConstants.BUNDLE_KEY_APP_VERSION_CHECK,
                            GoSingConstants.BUNDLE_VALUE_APP_VERSION_NOT_CHECK);
                    startActivity(intent);
                    getActivity().finishAffinity();
                }
            }
        });

        // 통신 실패
        mainViewModel.getSession().observe(this, isFail -> {
            if (!isFail) {
                Toast.makeText(view.getContext(),
                        "다시 시도해 주세요",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mainViewModel.getDepositAccount().observe(this, dto -> {

            if (dto == null) {
                NoTitleDialog noTitleDialog = new NoTitleDialog();
                noTitleDialog.setContent(R.string.fragment_main_no_deposit_account);
                noTitleDialog.setUseYesOrNo(true);
                noTitleDialog.show(getFragmentManager(), "출금 계좌 다이얼로그");
                noTitleDialog.onDoneOnCliListener(view -> {
                    noTitleDialog.dismiss();
//                moveActivity(PointWithDrawalActivity.class);
                    moveActivity(RegisterAccountActivity.class);
                });

                noTitleDialog.onNoOnClickListener(view -> noTitleDialog.dismiss());
            } else {
                moveActivity(PointWithDrawalActivity.class);
            }
        });

    }

    private void moveActivityWidthDebug(Class moveClass) {
        onServiceReady();
//        if (BuildConfig.BUILD_TYPE.equals("debug")) {
//            Intent intent = new Intent(view.getContext(), moveClass);
//            startActivity(intent);
//        } else {
//            onServiceReady();
//        }
    }

    private void moveActivity(Class moveClass) {
        Intent intent = new Intent(view.getContext(), moveClass);
        startActivity(intent);
    }

    private void onServiceReady() {
        Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
    }

}
