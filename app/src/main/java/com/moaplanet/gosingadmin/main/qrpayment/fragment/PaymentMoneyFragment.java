package com.moaplanet.gosingadmin.main.qrpayment.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.interfaces.PriceWatcher;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.qrpayment.model.QrCodeViewModel;
import com.moaplanet.gosingadmin.utils.StringUtil;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class PaymentMoneyFragment extends BaseFragment {

    // 뮤모델
    private QrCodeViewModel qrCodeViewModel;
    // 로딩바
    private ProgressBar loadingBar;
    // 적립 및 비적립 결제 금액 지우기 버튼
    private ImageView btnClearSaveMoney, btnClearNoSaveMoney;
    // 적립 및 비적립 결제 금액 입력 화면
    private EditText etInputSaveMoney, etInputNoSaveMoney;
    private InputMethodManager imm;

    // 총결제 금액
    private TextView tvTotalPrice;
    private TextView tvReserveFund;
    private TextView tvGoSingPoint;

    // Qr코드로 결제하는 화면이동 버튼
    private Button btnQrPayment;

    private int myPoint = 0;

    @Override
    public int layoutRes() {
        return R.layout.fragment_payment_money;
    }

    @Override
    public void initView(View view) {
        loadingBar = view.findViewById(R.id.pr_fragment_payment_money_loading);
        loadingBar.setVisibility(View.GONE);

        btnClearSaveMoney = view.findViewById(R.id.iv_main_save_money_clear);
        btnClearNoSaveMoney = view.findViewById(R.id.iv_main_no_save_money_clear);

        etInputSaveMoney = view.findViewById(R.id.et_main_input_save_money);
        etInputNoSaveMoney = view.findViewById(R.id.et_main_input_no_save_money);

        tvTotalPrice = view.findViewById(R.id.tv_fragment_payment_main_total_price);
        tvTotalPrice.setText(getString(R.string.fragment_payment_money_won, "0"));

        tvReserveFund = view.findViewById(R.id.tv_main_customer_reserve_fund_content);
        tvReserveFund.setText(getString(R.string.fragment_payment_money_won, "0"));

        btnQrPayment = view.findViewById(R.id.make_qr_code);

        tvGoSingPoint = view.findViewById(R.id.tv_main_balance_gosing_point_content);
    }

    @Override
    public void initListener() {
        CommonTitleBar titleBar = view.findViewById(R.id.title_fragment_payment_money_title_bar);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });

        // 적립 결제 금액 지우기
        RxView.clicks(btnClearSaveMoney)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    etInputSaveMoney.setText("0");
                    etInputSaveMoney.requestFocus();
                    imm.showSoftInput(etInputSaveMoney, 0);
                });

        // 비적립 결제 금액 지우기
        RxView.clicks(btnClearNoSaveMoney)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    etInputNoSaveMoney.setText("0");
                    etInputNoSaveMoney.requestFocus();
                    imm.showSoftInput(etInputNoSaveMoney, 0);
                });

        PriceWatcher savePriceWatcher = new PriceWatcher(etInputSaveMoney);
        savePriceWatcher.setCallback(new PriceWatcher.onPriceWatcherCallback() {
            @Override
            public void onTextChange(String completePrice, int price) {
                if (qrCodeViewModel != null) {
                    qrCodeViewModel.setInputSavePrice(completePrice);
                }
            }
        });
        etInputSaveMoney.addTextChangedListener(savePriceWatcher);

//        etInputSaveMoney.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (qrCodeViewModel != null) {
//                    qrCodeViewModel.setInputSavePrice(editable.toString());
//                }
//            }
//        });

        PriceWatcher noSavePriceWatcher = new PriceWatcher(etInputNoSaveMoney);
        noSavePriceWatcher.setCallback(new PriceWatcher.onPriceWatcherCallback() {
            @Override
            public void onTextChange(String completePrice, int price) {
                if (qrCodeViewModel != null) {
                    qrCodeViewModel.setInputNoSavePrice(completePrice);
                }
            }
        });
        etInputNoSaveMoney.addTextChangedListener(noSavePriceWatcher);
//        etInputNoSaveMoney.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (qrCodeViewModel != null) {
//                    qrCodeViewModel.setInputNoSavePrice(editable.toString());
//                }
//            }
//        });

        // 결제 화면으로 이동
        RxView.clicks(btnQrPayment)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onMoveNavigation(R.id.action_fragment_qr_payment));
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        qrCodeViewModel.getServerConnectFail().observe(this, type ->
                Toast.makeText(
                        view.getContext(),
                        "서버와 원활하지 않습니다.",
                        Toast.LENGTH_SHORT).show());

        qrCodeViewModel.getLoading().observe(this, loadingType -> {
            if (loadingType) {
                onStartLoading();
            } else {
                onStopLoading();
            }
        });

        qrCodeViewModel.myPoint.observe(this, point -> {
            myPoint = point;
            tvGoSingPoint.setText(getString(R.string.common_price_won,
                    StringUtil.convertCommaPrice(point)));
        });

        // 적립 결제 금액 세팅
//        qrCodeViewModel.getInputSavePrice().observe(this, price -> {
//            //todo 수정 필요
//            int cp = etInputSaveMoney.getSelectionStart();
//            int startLen = etInputSaveMoney.getText().length();
//            int wonLen;
//            if (etInputSaveMoney.getText().length() == 1) {
//                wonLen = -1;
//            } else {
//                wonLen = 0;
//            }
//            etInputSaveMoney.setText(getString(R.string.fragment_payment_money_won, price));
//            int endLen = etInputSaveMoney.getText().length();
//
//            etInputSaveMoney.setSelection((cp + (endLen - startLen)) + wonLen);
//        });

//        // 비적립 결제 금액 세팅
//        qrCodeViewModel.getInputNoSavePrice().observe(this, price -> {
//            //todo 수정 필요
//            int cp = etInputNoSaveMoney.getSelectionStart();
//            int startLen = etInputNoSaveMoney.getText().length();
//            int wonLen;
//            if (etInputNoSaveMoney.getText().length() == 1) {
//                wonLen = -1;
//            } else {
//                wonLen = 0;
//            }
//            etInputNoSaveMoney.setText(getString(R.string.fragment_payment_money_won, price));
//            int endLen = etInputNoSaveMoney.getText().length();
//
//            etInputNoSaveMoney.setSelection((cp + (endLen - startLen)) + wonLen);
//        });

        // 총 결제금액 세팅
        qrCodeViewModel.getTotalPaymentPrice().observe(this, totalPrice ->
                tvTotalPrice.setText(getString(R.string.fragment_payment_money_won, totalPrice)));

        // 적립 금액 초기화
        qrCodeViewModel.getSaveMoney().observe(this, savePrice -> {
            tvReserveFund.setText(getString(R.string.fragment_payment_money_won, savePrice));
            int price = Integer.valueOf(savePrice.replace(",", ""));
            tvGoSingPoint.setText(getString(R.string.fragment_payment_money_won,
                    StringUtil.convertCommaPrice(myPoint - price)));
        });

        qrCodeViewModel.getSession().observe(this, isSession -> {
            if (!isSession) {
                onNotSession();
            }
        });
    }

    /**
     * 로딩 시작
     */
    private void onStartLoading() {
        qrCodeViewModel.setIsLoadingServer(true);
        if (getActivity() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        loadingBar.setVisibility(View.VISIBLE);
    }

    /**
     * 로딩 종료
     */
    private void onStopLoading() {
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        loadingBar.setVisibility(View.GONE);
        qrCodeViewModel.setIsLoadingServer(false);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            qrCodeViewModel = ViewModelProviders.of(getActivity()).get(QrCodeViewModel.class);
            qrCodeViewModel.onPaymentInfoInit();
            imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        }
    }
}
