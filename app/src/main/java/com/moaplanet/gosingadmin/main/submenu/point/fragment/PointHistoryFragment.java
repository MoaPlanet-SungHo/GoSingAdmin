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
import com.moaplanet.gosingadmin.main.submenu.point.model.res.ResPointHistoryDto;
import com.moaplanet.gosingadmin.main.submenu.point.model.viewmodel.PointHistoryViewModel;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;
import com.orhanobut.logger.Logger;

import retrofit2.Call;

public class PointHistoryFragment extends BaseFragment {


    //    private String startDate = "startDate";
//    private String endDate = "startDate";
//    private String type = "type";
    // 뷰 타입
    private String viewType = GoSingConstants.BUNDLE_VALUE_POINT_VIEW_ALL;
    // 포인트 내역 리스트 어뎁터
    private PointHistoryListAdapter mAdapter;
    // 뷰모댈
    private PointHistoryViewModel mViewModel;

    @Override
    public int layoutRes() {
        return R.layout.fragment_point_history;
    }

    @Override
    protected void initFragment() {
        super.initFragment();
        if (getArguments() != null) {
            viewType = getArguments().getString(GoSingConstants.BUNDLE_KEY_TYPE_POINT_VIEW);
//            startDate = getArguments().getString("defaultStartDate");
//            endDate = getArguments().getString("defaultEndDate");
        }

    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (getActivity() != null) {
            mViewModel = ViewModelProviders.of(getActivity()).get(PointHistoryViewModel.class);
        }
    }

    @Override
    public void initView(View view) {
        RecyclerView rvPointHistory = view.findViewById(R.id.rv_point_history);
        rvPointHistory.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mAdapter = new PointHistoryListAdapter();
        mAdapter.setViewType(viewType);
        mAdapter.setFragmentManager(getFragmentManager());
        rvPointHistory.setAdapter(mAdapter);

//        onPointHistoryList();
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
                        onPointHistoryList();
                    }
                }
            });

        }

    }

    /**
     * 포인트 내역 불러오기 서버 통신
     */
    private void onPointHistoryList() {
        RetrofitService
                .getInstance()
                .getGoSingApiService()
                .onServerPointHistoryList(
                        mViewModel.getStartDate(),
                        mViewModel.getEndDate(),
                        viewType)
                .enqueue(new MoaAuthCallback<ResPointHistoryDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResPointHistoryDto> call, ResPointHistoryDto resModel) {
                        if (resModel.getDetailCode() == NetworkConstants.DETAIL_CODE_SUCCESS) {
                            mAdapter.setList(resModel.getPointHistoryDtoList());
                        } else {
                            onNetworkConnectFail();
                        }
                    }

                    @Override
                    public void onFinalFailure(Call<ResPointHistoryDto> call, boolean isSession, Throwable t) {
                        onNetworkConnectFail();
                    }

                    @Override
                    public void onFinalNotSession() {
                        super.onFinalNotSession();
                        onNotSession();
                    }
                });
    }

}
