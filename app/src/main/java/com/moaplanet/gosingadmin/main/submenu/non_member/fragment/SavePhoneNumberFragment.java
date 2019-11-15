package com.moaplanet.gosingadmin.main.submenu.non_member.fragment;

import android.content.Context;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.non_member.model.NonMemberSavePointViewModel;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.util.Objects;

public class SavePhoneNumberFragment extends BaseFragment {

    // 뷰 모델
    private NonMemberSavePointViewModel mViewModel;

    private EditText etPhoneNumber;
    private Button btnDone;
    // 로딩 뷰
    private ProgressBar mLoading;

    // 해드폰 번호 입력했는지 플래그값
    private boolean mIsPhoneNumber = false;

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(NonMemberSavePointViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_save_phone_number;
    }

    @Override
    public void initView(View view) {
        mLoading = view.findViewById(R.id.pb_fragment_save_phone_number_loading);
        etPhoneNumber = view.findViewById(R.id.et_save_phone_number_input_number);
        btnDone = view.findViewById(R.id.btn_save_phone_number_done);
    }

    @Override
    public void initListener() {
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
//        btnDone.setOnClickListener(view -> onMoveNavigation(R.id.action_fragment_save_point));
        btnDone.setOnClickListener(view1 -> {
            if (mIsPhoneNumber) {

                // 키보드 포커스 제거
                etPhoneNumber.clearFocus();
                InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);

                // 충전 화면으로 이동
                onMoveNavigation(R.id.action_fragment_save_point);
            }
        });

        mViewModel.searchGoSingPoint();
        initObserve();

    }

    /**
     * 뷰 모델 옵저브 처리
     */
    private void initObserve() {

        // 로딩 처리
        mViewModel.getIsLoading().observe(this,
                isLoading -> onInitLoading(mLoading, isLoading));

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
