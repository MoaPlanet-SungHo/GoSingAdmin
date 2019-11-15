package com.moaplanet.gosingadmin.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public abstract class BaseFragment extends Fragment {
    public View view;

    public abstract int layoutRes();

    public abstract void initView(View view);

    public abstract void initListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layoutRes(), container, false);
        initFragment();
        initView(view);
        initListener();
        return view;
    }

    protected void onBackNavigation() {
        Navigation.findNavController(view).popBackStack();
    }

    protected void onMoveNavigation(@IdRes int actionId) {
        Navigation.findNavController(view).navigate(actionId);
    }

    protected void initFragment() {
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

}
