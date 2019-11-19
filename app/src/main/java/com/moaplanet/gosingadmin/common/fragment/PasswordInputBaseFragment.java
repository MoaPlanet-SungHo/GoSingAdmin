package com.moaplanet.gosingadmin.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.chaos.view.PinView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.Objects;

/**
 * 비밀번호 입력화면 공통
 */
public abstract class PasswordInputBaseFragment extends BaseFragment {

    public static String BUNDLE_REQUEST_FROM_VIEW = "BUNDLE_REQUEST_FROM_VIEW";
    public static String BUNDLE_REQUEST_FROM_VIEW_JOIN = "BUNDLE_REQUEST_FROM_VIEW_JOIN";
    public static String BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER = "BUNDLE_REQUEST_FROM_VIEW_ACCOUNT_REGISTER";

    public abstract void checkPasswordViewType();           //비밀번호 FromView 타입에 따른 분기처리

    public abstract void checkPassword(String password);       //password 체크 로직 구현

    public abstract String titleText();

    protected PinView passwordPinView;
    protected TextView tvPasswordError;
    protected TextView tvPasswordInputTitle;
    protected TextView tvExplanation;
    private CommonTitleBar commonTitle;

    public String viewType;

    @Override
    public int layoutRes() {
        return R.layout.fragment_password_input;
    }

    @Override
    public void initView(View view) {

        commonTitle = view.findViewById(R.id.common_password_input_toolbar);
        commonTitle.setTitle(titleText());

        passwordPinView = view.findViewById(R.id.pinview_fragment_password_input);
        visibleKeyboard(passwordPinView);

        tvPasswordError = view.findViewById(R.id.tv_fragment_password_input_validation);
        tvPasswordError.setVisibility(View.INVISIBLE);
        tvPasswordInputTitle = view.findViewById(R.id.tv_fragment_password_input_title);

        tvExplanation = view.findViewById(R.id.tv_fragment_password_input_explanation);

        checkPasswordViewType();
    }

    @Override
    public void initListener() {
//        commonTitle.setOnClickListener(view -> Navigation.findNavController(view).popBackStack());
        commonTitle.setBackButtonClickListener(view1 -> onBackNavigation());
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 6) {
                // passwordPinView 뷰에 패스워드 6개가 사용자에게 다 입력됬다는것을 보여주기 위함
                new Handler().postDelayed(() -> checkPassword(editable.toString()), 100);
            }
        }
    };

    private void visibleKeyboard(View view) {
//        if (view instanceof EditText) {
//            view.requestFocus();
//            new Handler().postDelayed(() -> {
//                //키보드 올리기
//                InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
//                Objects.requireNonNull(imm).showSoftInput(view, 0);
//            }, 200);
//        }
    }

    private void goneKeyboard(View view) {
        if (view instanceof EditText) {
            view.clearFocus();
            //키보드 내리기
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        passwordPinView.addTextChangedListener(watcher);
    }

    @Override
    public void onPause() {
        passwordPinView.removeTextChangedListener(watcher);
        goneKeyboard(passwordPinView);
        super.onPause();
    }

}
