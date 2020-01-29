package com.moaplanet.gosingadmin.main.slide_menu.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.view.RxView;
import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.activity.WebViewActivity;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.common.view.CommonTitleBar;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.interfaces.AdapterClick;
import com.moaplanet.gosingadmin.main.slide_menu.event.adapter.EventAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class EventFragment extends BaseFragment {

    // 뷰 모델
    private EventViewModel viewModel;
    // 이벤트 리스트 어뎁터
    private EventAdapter eventAdapter;
    // 로딩
    private View loadingBar;
    // 리스트뷰
    private RecyclerView rvEvent;

    // 이벤트 리스트
    private List<EventDTO.EventModel> eventList;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_event;
    }

    @Override
    public void initView(View view) {
        eventList = new ArrayList<>();
        loadingBar = view.findViewById(R.id.pr_fragment_event_loading);
        rvEvent = view.findViewById(R.id.rv_event_list);
        rvEvent.setLayoutManager(new LinearLayoutManager(view.getContext()));
        eventAdapter = new EventAdapter();
        rvEvent.setAdapter(eventAdapter);
    }

    @Override
    public void initListener() {

        // 상단 뒤로가기 버튼 클릭
        CommonTitleBar titleBar = view.findViewById(R.id.common_fragment_event_title_bar);
        RxView.clicks(titleBar.getBtnBack())
                .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> onBackNavigation());

        // 어댑터에서 클릭 관련
        eventAdapter.setCallback(new AdapterClick<EventDTO.EventModel>() {
            @Override
            public void click(EventDTO.EventModel model) {
                if (viewModel != null) {
                    String moveUrl = viewModel.getEventUrl(model.getEventSeq());
                    Logger.d("이벤트 url : " + moveUrl);
                    Intent intent = new Intent(view.getContext(), WebViewActivity.class);
                    intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_TITLE,
                            getString(R.string.fragment_event_title_bar));
                    intent.putExtra(GoSingConstants.BUNDLE_KEY_WEB_VIEW_URL, moveUrl);
                    startActivity(intent);
                }
            }

            @Override
            public void click(EventDTO.EventModel model, int type) {

            }
        });
    }

    @Override
    protected void initObserve() {
        super.initObserve();

        if (viewModel != null) {

            // 세션 만료 관련
            viewModel.getExpireSession().observe(getViewLifecycleOwner(), expireSession -> {
                if (expireSession) {
                    onExpireSession();
                }
            });

            // 서버 통신 실패시
            viewModel.getFailNetwork().observe(getViewLifecycleOwner(), failNetwork -> {
                if (failNetwork) {
                    onNetworkConnectFail();
                }
            });

            // 이벤트 리스트
            viewModel.getEventList().observe(getViewLifecycleOwner(), list -> {
                eventList.addAll(list);
//                eventAdapter.submitList(list);
                eventAdapter.submitList(eventList);
                eventAdapter.notifyDataSetChanged();
                Logger.d("이벤트 리스트 : " + eventList.size());
                loadingBar.setVisibility(View.GONE);

                // 진행중인 이벤트 없음문구 표시 컨트롤
                View emptyView = view.findViewById(R.id.tv_fragment_event_empty);
                if (list.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.postEventList();

        LinearLayoutManager layoutManager = (LinearLayoutManager) rvEvent.getLayoutManager();
        rvEvent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();


                if (lastVisiblePosition + visibleItemCount >= totalItemCount) {
                    viewModel.postEventList();
                }

            }
        });

    }
}
