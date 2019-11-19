package com.moaplanet.gosingadmin.intro.sign_up.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.CreateAccountViewModel;
import com.moaplanet.gosingadmin.intro.sign_up.model.viewmodel.SignUpViewModel;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.moaplanet.gosingadmin.utils.ViewUtil;

import java.util.Objects;

/**
 * 회원가입 아이디 패스워드 일벽 화면
 */
public class CreateAccountFragment extends BaseFragment {

    // 이메일, 비밀번호, 비밀번호 확인 입력화면
    private EditText etEmail, etPw, etPwCheck;
    private Button nextStep;

    // 이메일 에러 뷰
    private TextView mTvEmailErr, mTvPwErr, mTvPwCheckErr;

    // 뷰 모델
    private SignUpViewModel signUpViewModel;
    private CreateAccountViewModel mViewModel;

    @Override
    protected void initFragment() {
        super.initFragment();
        mViewModel = ViewModelProviders.of(this).get(CreateAccountViewModel.class);
        if (getActivity() != null) {
            signUpViewModel = ViewModelProviders.of(getActivity()).get(SignUpViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_create_account;
    }

    @Override
    public void initView(View view) {

        // 이메일 관련 초기화
        etEmail = view.findViewById(R.id.et_create_account_email);
        etEmail.setFilters(new InputFilter[]{
                StringUtil.notEmptyFilter()
        });
        mTvEmailErr = view.findViewById(R.id.tv_create_account_email_err);
        mTvEmailErr.setVisibility(View.GONE);

        // 패스워드 관련 초기화
        etPw = view.findViewById(R.id.et_create_account_pw);
        etPw.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(20),
                StringUtil.notEmptyFilter()
        });
        mTvPwErr = view.findViewById(R.id.tv_create_account_pw_err);
        mTvPwErr.setVisibility(View.GONE);

        // 패스워드 체크 관련 초기화
        etPwCheck = view.findViewById(R.id.et_create_account_pw_check);
        etPwCheck.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(20),
                StringUtil.notEmptyFilter()
        });
        mTvPwCheckErr = view.findViewById(R.id.tv_create_account_pw_check_err);
        mTvPwCheckErr.setVisibility(View.GONE);


        nextStep = view.findViewById(R.id.btn_create_account_done);

    }

    @Override
    public void initListener() {

        // 타이틀바 뒤로가기
        CommonTitleBar commonTitleBar = view.findViewById(R.id.common_create_account_title_bar);
        commonTitleBar.setBackButtonClickListener(view -> onBackNavigation());

        // 다음 버튼 클릭
        nextStep.setOnClickListener(view -> {
            mViewModel.onCheckAccount(
                    etPw.getText().toString(),
                    etPwCheck.getText().toString());
//            Bundle bundle = new Bundle();
//            bundle.putString(
//                    GoSingConstants.BUNDLE_KEY_TYPE_PASSWORD,
//                    GoSingConstants.BUNDLE_VALUE_NEW_PASSWORD
//            );
//
//            Navigation.findNavController(this.view).navigate(
//                    R.id.action_fragment_sign_up_input_password, bundle
//            );
        });
//                mViewModel.onCheckAccount(
//                        etPw.getText().toString(),
//                        etPwCheck.getText().toString()));

        // 이메일 입력 리스터
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setEmail(editable.toString());
            }
        });
    }

    @Override
    public void onPause() {
        view.clearFocus();
        //키보드 내리기
//        InputMethodManager imm = (InputMethodManager) Objects
//                .requireNonNull(view.getContext())
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        ViewUtil.onHideKeyboard(view);
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        // 사용자가 입력한 아이디가 이메일 타입인지 유무
        mViewModel.getEmailErrMsg().observe(this,
                viewType -> mTvEmailErr.setVisibility(viewType));

        // 비밀번호 에러메시지 표시 유무
        mViewModel.getPwErrMsg().observe(this, viewType -> mTvPwErr.setVisibility(viewType));

        // 비밀번호 확인 에러메시지 표시 유무
        mViewModel.getPwCheckErrMsg().observe(this,
                viewType -> mTvPwCheckErr.setVisibility(viewType));

        // 회원가입 데이터 체크 완료
        mViewModel.getAccountComplete().observe(this, complete -> {
            if (complete) {
                mViewModel.setmAccountComplete(false);
                signUpViewModel.setEmail(mViewModel.getEmail().getValue());
                signUpViewModel.setPw(mViewModel.getPw().getValue());

                Bundle bundle = new Bundle();
                bundle.putString(
                        GoSingConstants.BUNDLE_KEY_TYPE_PASSWORD,
                        GoSingConstants.BUNDLE_VALUE_NEW_PASSWORD
                );

                Navigation.findNavController(this.view).navigate(
                        R.id.action_fragment_sign_up_input_password, bundle
                );
            }
        });

    }

}
