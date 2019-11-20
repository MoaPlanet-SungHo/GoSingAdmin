package com.moaplanet.gosingadmin.main.qrpayment.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.qrpayment.dto.req.ReqCreateQrCodeDto;
import com.moaplanet.gosingadmin.main.qrpayment.model.QrCodeViewModel;
import com.moaplanet.gosingadmin.main.qrpayment.model.QrPaymentViewModel;
import com.orhanobut.logger.Logger;

import java.util.Timer;
import java.util.TimerTask;


public class QrPaymentFragment extends BaseFragment {

    // 뷰모델
    private QrCodeViewModel qrCodeViewModel;
    private QrPaymentViewModel qrPaymentViewModel;

    // 로딩
    private ProgressBar loading;

    // 타이머 관련
    private TextView tvFiveTimer;
    private TimerTask timerTask;
    private Timer timer = new Timer();

    // 가맹점 이름
    private TextView tvStoreName;

    // QrCode 이미지 뷰
    private ImageView ivQrCode;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            if (qrCodeViewModel == null) {
                qrCodeViewModel = ViewModelProviders.of(getActivity()).get(QrCodeViewModel.class);

                // 총 결제금액 초기화
                TextView tvPriceTotal = view.findViewById(R.id.tv_main_total_payment_amount_price);
                initPrice(tvPriceTotal, qrCodeViewModel.getTotalPaymentPrice().getValue());

                // 고객 적립금 초기화
                TextView tvPriceReserve = view.findViewById(R.id.tv_main_customer_reserve_fund_price);
                initPrice(tvPriceReserve, qrCodeViewModel.getSaveMoney().getValue());

            }
        }
        if (qrPaymentViewModel == null) {
            qrPaymentViewModel = ViewModelProviders.of(this).get(QrPaymentViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_input_qr;
    }

    @Override
    public void initView(View view) {
        // 로딩 초기화
        loading = view.findViewById(R.id.pr_fragment_qr_payment_loading);
        loading.setVisibility(View.GONE);

        // 타이머
        tvFiveTimer = view.findViewById(R.id.show_five_minute);

        // 가맹점 이름
        tvStoreName = view.findViewById(R.id.tv_main_karaoke_name);

        // QRCode
        ivQrCode = view.findViewById(R.id.iv_fragment_qr_payment_qr_code);

    }

    /**
     * QrCode 타이머 실행
     */
    private void startTimerTask() {
        stopTimerTask();

        timerTask = new TimerTask() {
            int count = 300;

            @Override
            public void run() {
                count--;
                tvFiveTimer.post(() -> {

                    int day = count / (60 * 60 * 24);
                    int hour = (count - day * 60 * 60 * 24) / (60 * 60);
                    int minute = (count - day * 60 * 60 * 24 - hour * 3600) / 60;
                    int second = count % 60;
                    tvFiveTimer.setText("0" + minute + ":" + second);

                    if (minute == 0 && second == 0) {
                        stopTimerTask();
                        Logger.d("타이머 종료");
                        createQrCodeDialogShow();
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 타이머 중지
     */
    private void stopTimerTask() {
        if (timerTask != null) {
            tvFiveTimer.setText("0초");
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    public void initListener() {
        // 타이틀바
        CommonTitleBar titleBar = view.findViewById(R.id.common_inputqr_title_bar);
        titleBar.setBackButtonClickListener(view -> onBackNavigation());
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        // 가맹점 이름 초기화
        qrPaymentViewModel.getStoreName().observe(this,
                storeName -> tvStoreName.setText(storeName));

        qrPaymentViewModel.getQrCodeUrl().observe(this, qrCodeUrl ->
                        Glide.with(view)
                                .load(qrCodeUrl)
                                .error(R.drawable.bg_err_qr_code)
                                .placeholder(R.drawable.bg_err_qr_code)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e,
                                                                Object model,
                                                                Target<Drawable> target,
                                                                boolean isFirstResource) {
//                                Toast.makeText(view.getContext(),
//                                        "다시 시도해 주세요",
//                                        Toast.LENGTH_SHORT).show();
//                                onBackNavigation();
                                        // 실패시
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource,
                                                                   Object model,
                                                                   Target<Drawable> target,
                                                                   DataSource dataSource,
                                                                   boolean isFirstResource) {
                                        // 성공시
                                        return false;
                                    }
                                })
                                .into(ivQrCode)
        );

        qrPaymentViewModel.getConnectServerResult().observe(this, result -> {
            if (result) {
                startTimerTask();
                onStopLoading();
            } else {
                Toast.makeText(view.getContext(),
                        "재시도해 주세요",
                        Toast.LENGTH_SHORT).show();
                onBackNavigation();
            }
        });
    }

    /**
     * QRCode 재생성 유무 생성 다이얼로그 보여줌
     */
    private void createQrCodeDialogShow() {
        NoTitleDialog noTitleDialog = new NoTitleDialog();
        noTitleDialog.setContent(R.string.fragment_qr_payment_qr_code_dialog);
        noTitleDialog.setUseYesOrNo(true);
        noTitleDialog.show(getChildFragmentManager(), "QRCodeDialog");
        // 확인 버튼 클릭시
        noTitleDialog.onDoneOnCliListener(view -> {
            initReqQrCodeDto();
            noTitleDialog.dismiss();
            onStartLoading();
        });
        // 아니오 버튼 클릭시
        noTitleDialog.onNoOnClickListener(view -> {
            noTitleDialog.dismiss();
            onBackNavigation();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onStartLoading();
        initReqQrCodeDto();
    }

    /**
     * QRCode 를 생성할때 사용할 reqModel 객세 초기화
     */
    private void initReqQrCodeDto() {
        ReqCreateQrCodeDto reqCreateQrCodeDto = new ReqCreateQrCodeDto();
        reqCreateQrCodeDto.setQrCodePk(qrCodeViewModel.getQrCodePk().getValue());
        reqCreateQrCodeDto.setReservePrice(qrCodeViewModel.getInputSavePrice().getValue());
        reqCreateQrCodeDto.setNoReservePrice(qrCodeViewModel.getInputNoSavePrice().getValue());

        qrPaymentViewModel.onCreateQrCode(reqCreateQrCodeDto);
    }

    /**
     * 가격 초기화
     *
     * @param tvPrice 가격 뷰
     * @param price   초기화할 가격
     */
    private void initPrice(TextView tvPrice, String price) {
        if (price == null) {
            price = "0";
        }
        tvPrice.setText(getString(
                R.string.fragment_payment_money_won,
                price
        ));
    }

    @Override
    public void onDestroy() {
        stopTimerTask();
        super.onDestroy();
    }

    /**
     * 로딩 시작
     */
    private void onStartLoading() {
        qrCodeViewModel.setIsLoadingServer(true);
        if (getActivity() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        loading.setVisibility(View.VISIBLE);
    }

    /**
     * 로딩 종료
     */
    private void onStopLoading() {
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        loading.setVisibility(View.GONE);
        qrCodeViewModel.setIsLoadingServer(false);
    }

}
