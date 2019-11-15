package com.moaplanet.gosingadmin.main.submenu.point.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.point.activity.PointHistoryActivity;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryListAdapter;
import com.moaplanet.gosingadmin.main.submenu.point.dto.res.ResPointHistoryDto;
import com.moaplanet.gosingadmin.network.NetworkConstants;
import com.moaplanet.gosingadmin.network.retrofit.MoaAuthCallback;
import com.moaplanet.gosingadmin.network.service.RetrofitService;

import retrofit2.Call;

public class PointHistoryFragment extends BaseFragment {


    private String startDate = "startDate";
    private String endDate = "startDate";
    private String type = "type";
    private String viewType = "all";
    private RecyclerView rvPointHistory;

    @Override
    public int layoutRes() {
        return R.layout.fragment_point_history;
    }

    @Override
    public void initView(View view) {
        rvPointHistory = view.findViewById(R.id.rv_point_history);
        rvPointHistory.setLayoutManager(new LinearLayoutManager(view.getContext()));
        PointHistoryListAdapter pointHistoryListAdapter = new PointHistoryListAdapter();
        pointHistoryListAdapter.setViewType(viewType);
        pointHistoryListAdapter.setFragmentManager(getFragmentManager());
        rvPointHistory.setAdapter(pointHistoryListAdapter);

        String str;
        str = getArguments().getString("type");
        Log.e("type!","type "+str);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewType = getArguments().getString("type");
            startDate = getArguments().getString("defaultStartDate");
            endDate = getArguments().getString("defaultEndDate");
        }
        onPointHistoryList();
    }

    public void onPointHistoryList() {
        PointHistoryListAdapter pointHistoryListAdapter = new PointHistoryListAdapter();
        RetrofitService.getInstance().getGoSingApiService().onServerPointHistoryList(setString(startDate), setString(endDate), setSearchCount(viewType))
                .enqueue(new MoaAuthCallback<ResPointHistoryDto>(
                        RetrofitService.getInstance().getMoaAuthConfig(),
                        RetrofitService.getInstance().getSessionChecker()
                ) {
                    @Override
                    public void onFinalResponse(Call<ResPointHistoryDto> call, ResPointHistoryDto resModel) {
                        if (resModel.getStateCode() == NetworkConstants.STATE_CODE_SUCCESS) {
                            if (resModel.getDetailCode() == 200) {
                                pointHistoryListAdapter.setList(resModel.getPointHistoryDtoList());
                                return;
                            }
                        }
                        serverErrorMsg();
                    }

                    @Override
                    public void onFinalFailure(Call<ResPointHistoryDto> call, boolean isSession, Throwable t) {
                        serverErrorMsg();
                    }
                });
    }

    private void serverErrorMsg() {
        PointHistoryActivity pointHistoryActivity = new PointHistoryActivity();
        Toast.makeText(pointHistoryActivity, "네트워크 에러입니다.", Toast.LENGTH_SHORT).show();
    }


    private String setSearchCount(String value) {
        switch (value) {
            case "all":
                return "1";
            case "deposit":
                return "2";
            case "withdrawal":
                return "3";
        }
        return "";
    }

    private String setString(String key) {
        String str;
        str = getArguments().getString(key);
        return str;
    }
}
