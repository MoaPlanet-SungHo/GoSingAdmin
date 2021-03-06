package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.dialog.CommonTitleNoUnderlineDialog;
import com.moaplanet.gosingadmin.common.dialog.NoTitleDialog;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.model.viewmodel.BaseActivityViewModel;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.main.submenu.charge.activity.ChargeActivity;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.NonMemberSavePointViewModel;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.ResPointSaveNonMemberCheckDTO;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;

public class SavePhoneNumberFragment extends BaseFragment {

    // 뷰 모델
    private NonMemberSavePointViewModel mViewModel;
    private BaseActivityViewModel mActivityViewModel;

    private EditText etPhoneNumber;
    private Button btnDone;

    // 해드폰 번호 입력했는지 플래그값
    private boolean mIsPhoneNumber = false;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            if (mViewModel == null) {
                mViewModel = ViewModelProviders.of(getActivity()).get(NonMemberSavePointViewModel.class);
            }
            if (mActivityViewModel == null) {
                mActivityViewModel = ViewModelProviders.of(getActivity()).get(BaseActivityViewModel.class);
            }
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_save_phone_number;
    }

    @Override
    public void initView(View view) {
        etPhoneNumber = view.findViewById(R.id.et_save_phone_number_input_number);
        btnDone = view.findViewById(R.id.btn_save_phone_number_done);
    }

    @Override
    public void initListener() {

        // 포인트 충전 화면으로 이동
        Button btnCharge = view.findViewById(R.id.btn_fragment_save_phone_number_charge);

        RxView.clicks(btnCharge)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    Intent intent = new Intent(view.getContext(), ChargeActivity.class);
                    startActivity(intent);
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });

        // 핸드폰  번호일시 "-" 추가
        etPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 핸드폰 번호인지 체크
                if (StringUtil.isPhoneNumber(etPhoneNumber.getText().toString())) {
                    mIsPhoneNumber = true;
                    etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_checkbox_press, 0);
                } else {
                    mIsPhoneNumber = false;
                    etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_checkbox_nor, 0);
                }
            }
        });

        // 포인트 적립 화면으로 이동
        RxView.clicks(btnDone)
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (mIsPhoneNumber) {
                        nonMemberCheck();
                    }
                });

        CommonTitleBar commonTitleBar = view.findViewById(R.id.common_save_phone_number_title_bar);
        RxView.clicks(commonTitleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.searchGoSingPoint();
    }

    private void nonMemberCheck() {

        mActivityViewModel.setIsLoading(true);

        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerPointSaveNonMemberCheck(etPhoneNumber.getText().toString())
                .enqueue(new MoaAuthCallback<ResPointSaveNonMemberCheckDTO>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResPointSaveNonMemberCheckDTO> call,
                                                ResPointSaveNonMemberCheckDTO resModel) {

                        mActivityViewModel.setIsLoading(false);
                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            mViewModel.setmPhoneNumber(etPhoneNumber.getText().toString());
                            // 키보드 포커스 제거
                            etPhoneNumber.clearFocus();
                            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);

                            // 충전 화면으로 이동
                            onMoveNavigation(R.id.action_fragment_save_point);

                        } else if (resModel.getDetailCode() == 201) {
                            NoTitleDialog dialog = new NoTitleDialog();
                            dialog.setContent(R.string.fragment_save_phone_number_exist_user);
                            dialog.show(getChildFragmentManager(), "dialog");
                            dialog.onDoneOnCliListener(v -> dialog.dismiss());
                        } else {
                            NoTitleDialog dialog = new NoTitleDialog();
                            dialog.setContent(R.string.fragment_save_phone_number_two_over);
                            dialog.show(getChildFragmentManager(), "dialog");
                            dialog.onDoneOnCliListener(v -> dialog.dismiss());
                        }

                    }

                    @Override
                    public void onFinalFailure(Call<ResPointSaveNonMemberCheckDTO> call,
                                               boolean isSession, Throwable t) {
                        onNetworkConnectFail();
                        mActivityViewModel.setIsLoading(false);
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        mActivityViewModel.setIsLoading(false);
                        onNotSession();
                    }
                });
    }

    @Override
    protected void initObserve() {
        super.initObserve();

//        // 로딩 처리
        mViewModel.getIsLoading().observe(this,
                isLoading -> mActivityViewModel.setIsLoading(isLoading));

        // 세션이 없을떄
        mViewModel.getSession().observe(this, isSession -> {
            if (!isSession) {
                onNotSession();
            }
        });

        // 통신 실패
        mViewModel.getIsApiSuccess().observe(this, isSuccess -> {
            if (!isSuccess) {
                onNetworkConnectFail();
            }
        });

        mViewModel.getPoint().observe(this, point -> {
            TextView tvPoint = view.findViewById(R.id.tv_fragment_save_phone_number_point);
            tvPoint.setText(getString(R.string.fragment_payment_money_won, point));
        });
    }

}
