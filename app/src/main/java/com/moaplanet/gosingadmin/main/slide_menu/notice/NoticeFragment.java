package com.moaplanet.gosingadmin.main.slide_menu.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.WebViewActivity;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.interfaces.AdapterClick;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class NoticeFragment extends BaseFragment {

    // 뷰 모델
    private NoticeViewModel viewModel;

    // 공지사항 리스트 어뎁터
    private NoticeAdapter noticeAdapter;

    // 로딩바
    private View prLoading;
    private RecyclerView rvNotice;

    // 공지사항 리스트
    private List<NoticeDTO.NoticeModel> noticeList;

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

        noticeList = new ArrayList<>();

        // 리스트 세팅
        rvNotice = view.findViewById(R.id.rv_fragment_notice);
        rvNotice.setLayoutManager(new LinearLayoutManager(view.getContext()));
        noticeAdapter = new NoticeAdapter();
        rvNotice.setAdapter(noticeAdapter);

        prLoading = view.findViewById(R.id.pb_fragment_notice_loading);
    }

    @Override
    public void initListener() {

        // 뒤로가기 버튼 클릭시
        CommonTitleBar titleBar = view.findViewById(R.id.common_fragment_notice_title);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

        // 어뎁터에서 클릭 이벤트
        noticeAdapter.setAdapterClick(new AdapterClick<NoticeDTO.NoticeModel>() {
            @Override
            public void click(NoticeDTO.NoticeModel model) {
                Logger.i("클릭한 공지사항 정보 : " + new Gson().toJson(model));

                if (viewModel != null) {
                    Intent intent = new Intent(view.getContext(), WebViewActivity.class);
                    intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_TITLE,
                            getString(R.string.fragment_notice_title));
                    intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_URL,
                            viewModel.getNoticeUrl(model.getSeq()));
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), R.string.common_toast_network_fail,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void click(NoticeDTO.NoticeModel model, int type) {

            }
        });

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        if (viewModel != null) {

            // 공지사항 리스트
            viewModel.getNoticeList().observe(getViewLifecycleOwner(), list -> {
                noticeList.addAll(list);
//                noticeAdapter.submitList(list);
                noticeAdapter.submitList(noticeList);
                noticeAdapter.notifyDataSetChanged();
                prLoading.setVisibility(View.GONE);

                // 공지사항 없을떄 표시 문구 세팅
                View emptyView = view.findViewById(R.id.tv_fragment_notice_empty);
                if (list.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
            });

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
            viewModel.postNoticeList();

            LinearLayoutManager layoutManager = (LinearLayoutManager) rvNotice.getLayoutManager();
            rvNotice.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();


                    if (lastVisiblePosition + visibleItemCount >= totalItemCount) {
                        viewModel.postNoticeList();
                    }

                }
            });

        }
    }
}
