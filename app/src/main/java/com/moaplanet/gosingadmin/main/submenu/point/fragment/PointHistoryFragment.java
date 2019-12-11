package com.moaplanet.gosingadmin.main.submenu.point.fragment;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.constants.GoSingConstants;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryListAdapter;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryPagingAdapter;
import com.moaplanet.gosingadmin.main.submenu.point.model.viewmodel.PointHistoryViewModel;
import com.moaplanet.gosingadmin.main.submenu.point.model.viewmodel.PointViewModel;

public class PointHistoryFragment extends BaseFragment {

    // 뷰 타입
    private String viewType = GoSingConstants.BUNDLE_VALUE_POINT_VIEW_ALL;
    // 포인트 내역 리스트 어뎁터
    private PointHistoryListAdapter mAdapter;
    private PointHistoryPagingAdapter pagingAdapter;
    // 뷰모댈
    private PointHistoryViewModel mViewModel;
    private PointViewModel pointViewModel;
    // 로딩뷰
    private View mLoadingBar;

    private View viewPointListEmpty;

    @Override
    public int layoutRes() {
        return R.layout.fragment_point_history;
    }

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getArguments() != null) {
            viewType = getArguments().getString(GoSingConstants.BUNDLE_KEY_TYPE_POINT_VIEW);
        }
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(PointHistoryViewModel.class);
        }
        pointViewModel = ViewModelProviders.of(this).get(PointViewModel.class);
    }

    @Override
    public void initView(View view) {

        viewPointListEmpty = view.findViewById(R.id.cl_point_history_empty);
        mLoadingBar = view.findViewById(R.id.pb_fragment_point_history_loading);
        mLoadingBar.setVisibility(View.GONE);
        RecyclerView rvPointHistory = view.findViewById(R.id.rv_point_history);
        rvPointHistory.setLayoutManager(new LinearLayoutManager(view.getContext()));

        pagingAdapter = new PointHistoryPagingAdapter();
        mAdapter = new PointHistoryListAdapter();
        mAdapter.setFragmentManager(getFragmentManager());
//        rvPointHistory.setAdapter(mAdapter);
        rvPointHistory.setAdapter(pagingAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initObserve() {
        super.initObserve();

        if (mViewModel != null) {
            // 람다로 적용시 에러발생
            mViewModel.getSearchDateComplete().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean isComplete) {
                    if (isComplete) {
//                        onPointHistoryList();
                        pointViewModel.setStartDate(mViewModel.getStartDate());
                        pointViewModel.setEndDate(mViewModel.getEndDate());
                        pointViewModel.setPointType(viewType);
                        pointViewModel.onPointPaging();

                        if (pointViewModel != null) {
                            pointViewModel.pointList.observe(getViewLifecycleOwner(), list -> {
                                pagingAdapter.submitList(list);
                            });

                            pointViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
                                if (isLoading) {
                                    mLoadingBar.setVisibility(View.VISIBLE);
                                } else {
                                    mLoadingBar.setVisibility(View.GONE);
                                }
                            });

                            pointViewModel.isEmptyData.observe(getViewLifecycleOwner(), isEmptyData -> {
                                if (isEmptyData) {
                                    viewPointListEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    viewPointListEmpty.setVisibility(View.GONE);
                                }
                            });

                            pointViewModel.noSession.observe(getViewLifecycleOwner(), noSession -> {
                                if (noSession) {
                                    onNotSession();
                                }
                            });

                            pointViewModel.networkFail.observe(getViewLifecycleOwner(), networkFail -> {
                                if (networkFail) {
                                    onNetworkConnectFail();
                                }
                            });
                        }
                    }
                }
            });

        }

    }

    /**
     * 포인트 내역 불러오기 서버 통신
     */
    private void onPointHistoryList() {
//        mLoadingBar.setVisibility(View.VISIBLE);
//        RetrofitService
//                .getInstance()
//                .getGoSingApiService()
//                .onServerPointHistoryList(
//                        mViewModel.getStartDate(),
//                        mViewModel.getEndDate(),
//                        viewType)
//                .enqueue(new MoaAuthCallback<ResPointHistoryDto>(
//                        RetrofitService.getInstance().getMoaAuthConfig(),
//                        RetrofitService.getInstance().getSessionChecker()
//                ) {
//                    @Override
//                    public void onFinalResponse(Call<ResPointHistoryDto> call, ResPointHistoryDto resModel) {
//                        mLoadingBar.setVisibility(View.GONE);
//                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
//
//                            if (resModel.getPointHistoryDtoList() != null &&
//                                    resModel.getPointHistoryDtoList().size() > 0) {
//                                viewPointListEmpty.setVisibility(View.GONE);
//                            } else {
//                                viewPointListEmpty.setVisibility(View.VISIBLE);
//                            }
//                            mAdapter.setList(resModel.getPointHistoryDtoList());
//                        } else {
//                            onNetworkConnectFail();
//                        }
//                    }
//
//                    @Override
//                    public void onFinalFailure(Call<ResPointHistoryDto> call, boolean isSession, Throwable t) {
//                        mLoadingBar.setVisibility(View.GONE);
//                        onNetworkConnectFail();
//                    }
//
//                    @Override
//                    public void onFinalNotSession() {
//                        super.onFinalNotSession();
//                        mLoadingBar.setVisibility(View.GONE);
//                        onNotSession();
//                    }
//                });
    }

}
