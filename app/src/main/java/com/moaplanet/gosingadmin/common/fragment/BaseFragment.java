package com.moaplanet.gosingadmin.common.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.intro.IntroActivity;
import com.moaplanet.gosingadmin.intro.login.LoginActivity;

public abstract class BaseFragment extends Fragment {
    public View view;

    public abstract int layoutRes();

    public abstract void initView(View view);

    public abstract void initListener();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layoutRes(), container, false);
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initObserve();
    }

    protected void onBackNavigation() {
        Navigation.findNavController(view).popBackStack();
    }

    protected void onMoveNavigation(@IdRes int actionId) {
        Navigation.findNavController(view).navigate(actionId);
    }

    /**
     * fragment 초기화
     */
    protected void initFragment() {
    }

    /**
     * 뷰 모델 초기화
     */
    protected void initViewModel() {
    }

    /**
     * 로딩 시작
     */
    protected void onStartLoading(View viewLoading) {
        if (getActivity() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        viewLoading.setVisibility(View.VISIBLE);
    }

    /**
     * 로딩 종료
     */
    protected void onStopLoading(View viewLoading) {
        if (getActivity() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        viewLoading.setVisibility(View.GONE);
    }

    /**
     * 로딩 초기화
     *
     * @param loadingBar 로딩 바
     * @param type       로딩 타입 --> true : 로딩중 | false : 로딩중지
     */
    protected void onInitLoading(View loadingBar, boolean type) {
        if (type) {
            onStartLoading(loadingBar);
        } else {
            onStopLoading(loadingBar);
        }
    }

    /**
     * 세션 없을 경우
     */
    protected void onNotSession() {
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

    /**
     * 통신 실패시
     */
    protected void onNetworkConnectFail() {
        Toast.makeText(view.getContext(),
                "다시 시도해 주세요",
                Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * 뷰모델 옵저버 처리
     */
    protected void initObserve() {
    }

}
