package com.moaplanet.gosingadmin.common.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.moaplanet.gosingadmin.common.model.viewmodel.BaseActivityViewModel;

/**
 * 공통으로 사용할 액티비티
 */
public abstract class BaseActivity extends AppCompatActivity {

    public abstract int layoutRes();

    public abstract void initView();

    public abstract void initListener();

    // 뷰 모델
    private BaseActivityViewModel mBaseViewModel;

    // 로딩 유무 체크 변수
    private boolean mIsLoading = false;
    private View mLoadingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseViewModel = ViewModelProviders.of(this).get(BaseActivityViewModel.class);
        initActivity();
        setContentView(layoutRes());
        initView();
        initListener();
        initObserve();
    }

    @Override
    public void onBackPressed() {
        // 로딩중이지 않을때에만 백버튼 활성화
        if (!mIsLoading) {
            super.onBackPressed();
        }
    }

    /**
     * 로딩바 초기화
     */
    public void setLoadingBar(View loadingBar) {
        this.mLoadingBar = loadingBar;
    }

    public void initActivity(){}

    /**
     * 로딩 시작
     */
    protected void onLoadingStart() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mBaseViewModel.setIsLoading(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * 로딩 종료
     */
    protected void onLoadingStop() {
        mBaseViewModel.setIsLoading(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mLoadingBar.setVisibility(View.GONE);
    }

    /**
     * 뷰모델 옵저버 처리
     */
    protected void initObserve() {
        // 로딩 처리
        mBaseViewModel.getIsLoading().observe(this, isLoading -> {
            mIsLoading = isLoading;
        });
    }
}
