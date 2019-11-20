package com.moaplanet.gosingadmin.main.submenu.pointwithdrawal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.fragment.PasswordInputFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.charge.model.viewmodel.DepositWithoutBankbookViewModel;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.activity.BankSelectActivity;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.activity.PointWithDrawalActivity;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.DepositAccountViewModel;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.RegisterWithdrawalAccountViewModel;
import com.moaplanet.gosingadmin.main.submenu.pointwithdrawal.model.ResBankInfoDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.model.CommonResDto;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.ViewUtil;
import com.orhanobut.logger.Logger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 계좌등록/변경 Fragment
 */
public class AccountResisterFragment extends BaseFragment {

    public static String BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER = "BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER";
    public static String BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE = "BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE";

    private ConstraintLayout clAccountInfoGroup;
    private TextView tvGuideText;
    private Button btnAccountRegister;

    private CommonTitleBar commonTitle;

    // 사용자가 선택한 은행
    private TextView mTvSelectBank;
    private EditText mAccountName, mAccountNumber;

    private ResBankInfoDto.BankInformationDto mBankInformationDto;

    // 뷰모델
    private RegisterWithdrawalAccountViewModel viewModel;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            viewModel =
                    ViewModelProviders.of(getActivity()).get(RegisterWithdrawalAccountViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_account_register;
    }

    @Override
    public void initView(View view) {

        commonTitle = view.findViewById(R.id.title_account_register);
        clAccountInfoGroup = view.findViewById(R.id.cl_fragment_account_register_account_info_group);
        tvGuideText = view.findViewById(R.id.tv_account_register_guide_text);
        btnAccountRegister = view.findViewById(R.id.btn_fragment_account_register);

        mAccountNumber = view.findViewById(R.id.et_account_register_account_number);
        mAccountName = view.findViewById(R.id.tv_account_register_account_name);

        String fromView = "";
        if (getArguments() != null) {
            fromView = getArguments().getString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW);
        }

        if (fromView != null && fromView.equals(BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE)) {
            commonTitle.setTitle("계좌 변경");
            clAccountInfoGroup.setVisibility(View.VISIBLE);
            tvGuideText.setText("새로운 인출 계좌를 등록하세요.");
            btnAccountRegister.setText("변경");
        } else {
            commonTitle.setTitle("계좌 등록");
            clAccountInfoGroup.setVisibility(View.GONE);
            tvGuideText.setText("인출 계좌를 등록하세요.");
            btnAccountRegister.setText("등록");
        }

        mTvSelectBank = view.findViewById(R.id.tv_fragment_account_register_select_bank);

    }

    @Override
    public void initListener() {

        RxView.clicks(commonTitle.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });

        // 등록 화면
        RxView.clicks(btnAccountRegister)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (inputDataCheck()) {
                        onMoveNavigation(R.id.action_fragment_password_input);
                    }
                });

        // 은행 선택
        RxView.clicks(mTvSelectBank)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(view.getContext(), BankSelectActivity.class);
                    startActivityForResult(intent, 4000);
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            mBankInformationDto = new Gson().fromJson(data.getStringExtra("data"),
                    ResBankInfoDto.BankInformationDto.class);

            TextView tvBankName = view.findViewById(R.id.tv_fragment_account_register_select_bank);
            tvBankName.setText(mBankInformationDto.getBankName());
//            viewModel.setmBankInfo(bankInformationDto);
        }

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        viewModel.getmPinSuccess().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                onOpenVirualAccount();
            }
        });

    }

    private boolean inputDataCheck() {

        if (mTvSelectBank.getText().length() == 0) {
            Toast.makeText(view.getContext(),
                    "은행을 선택해 주세요",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mAccountNumber.getText().length() == 0) {
            Toast.makeText(view.getContext(),
                    "계좌번호를 입력해 주세요",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mAccountName.getText().length() == 0) {
            Toast.makeText(view.getContext(),
                    "예금주를 입력해 주세요.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void onOpenVirualAccount() {
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerOpenBankAccount(
                        mBankInformationDto.getBankCode(),
                        mBankInformationDto.getBankName(),
//                        viewModel.getmBankInfo().getValue().getBankCode(),
//                        viewModel.getmBankInfo().getValue().getBankName(),
                        "20"
                )
                .enqueue(new MoaAuthCallback<CommonResDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<CommonResDto> call, CommonResDto resModel) {

                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
//                            viewModel.setmAccountName(mAccountName.getText().toString());
//                            viewModel.setmAccountNumber(mAccountNumber.getText().toString());

//                            onMoveNavigation(R.id.action_fragment_withdrawal);
//                            onMoveNavigation(R.id.fragment_password_input);
                            Intent intent = new Intent(view.getContext(), PointWithDrawalActivity.class);
                            startActivity(intent);
                            if (getActivity() != null) {
                                getActivity().finish();
                            }
                        } else {
                            onNetworkConnectFail();
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<CommonResDto> call,
                                               boolean isSession, Throwable t) {
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        onNotSession();
                    }
                });
    }

    @Override
    public void onPause() {
        ViewUtil.onHideKeyboard(view);
        super.onPause();
    }
}
