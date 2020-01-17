package com.moaplanet.gosingadmin.main.slide_menu.notice;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class NoticeFragment extends BaseFragment {

    // 뷰 모델
    private NoticeViewModel viewModel;
    // 공지사항 리스트 어뎁터
    private NoticeAdapter noticeAdapter;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(NoticeViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initView(View view) {

        // 리스트 세팅
        RecyclerView rvNotice = view.findViewById(R.id.rv_fragment_notice);
        rvNotice.setLayoutManager(new LinearLayoutManager(view.getContext()));
        noticeAdapter = new NoticeAdapter();
        rvNotice.setAdapter(noticeAdapter);
    }

    @Override
    public void initListener() {

        CommonTitleBar titleBar = view.findViewById(R.id.common_fragment_notice_title);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        if (viewModel != null) {

            // 공지사항 리스트
            viewModel.getNoticeList().observe(getViewLifecycleOwner(),
                    list -> noticeAdapter.submitList(list));

            // 세션 만료
            viewModel.getExpireSession().observe(getViewLifecycleOwner(), expireSession -> {
                if (expireSession) {
                    onExpireSession();
                }
            });

            viewModel.getFailNetwork().observe(getViewLifecycleOwner(), failNetwork -> {
                if (failNetwork) {
                    onNetworkConnectFail();
                }
            });
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (viewModel != null) {
            viewModel.postNoticeList(1);
        }
    }
}
