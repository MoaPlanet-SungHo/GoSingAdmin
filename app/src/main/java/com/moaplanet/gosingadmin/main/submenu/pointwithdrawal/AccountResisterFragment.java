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

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
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

import retrofit2.Call;

/**
 * 계좌등록/변경 Fragment
 */
public class AccountResisterFragment extends BaseFragment {

    public static String BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER = "BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER";
    public static String BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE = "BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_CHANGE";

    private ConstraintLayout clAccountInfoGroup;
    private TextView tvGuideText;
    private Button btnAccountRegister;

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

        CommonTitleBar commonTitle = view.findViewById(R.id.title_account_register);
        commonTitle.setBackButtonClickListener(view1 -> getActivity().finish());
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

        // 등록 화면
        btnAccountRegister.setOnClickListener(view1 -> {
            if (mBankInformationDto != null) {
//                onOpenVirualAccount();
                onMoveNavigation(R.id.action_fragment_password_input);
            }
//            Bundle bundle = new Bundle();
//            bundle.putString(GoSingConstants.BUNDLE_REQUEST_FROM_VIEW,
//                    PasswordInputFragment.BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER);
//            Navigation.findNavController(view).navigate(R.id.action_fragment_password_input, bundle);

//            viewModel.setmAccountName(mAccountName.getText().toString());
//            viewModel.setmAccountNumber(mAccountNumber.getText().toString());
//
//            onMoveNavigation(R.id.action_fragment_withdrawal);

        });

        // 은행 선택
        mTvSelectBank.setOnClickListener(view1 -> {
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
