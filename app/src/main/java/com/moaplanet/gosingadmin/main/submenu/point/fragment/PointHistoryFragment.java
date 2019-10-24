package com.moaplanet.gosingadmin.main.submenu.point.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.point.adapter.PointHistoryListAdapter;

public class PointHistoryFragment extends BaseFragment {

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
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewType = getArguments().getString("type");
        }
    }
}
