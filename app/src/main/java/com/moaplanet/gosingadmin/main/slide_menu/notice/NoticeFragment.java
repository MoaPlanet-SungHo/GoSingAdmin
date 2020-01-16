package com.moaplanet.gosingadmin.main.slide_menu.notice;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;

public class NoticeFragment extends BaseFragment {

    private RecyclerView rvNotice;
    private NoticeViewModel viewModel;

    @Override
    protected void initViewModel() {
        super.initViewModel();
        if (viewModel != null) {
        }
    }

    @Override
    public int layoutRes() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initView(View view) {
        rvNotice = view.findViewById(R.id.rv_notice_list);
        rvNotice.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void initListener() {
        NoticeAdapter noticeAdapter = new NoticeAdapter();
        rvNotice.setAdapter(noticeAdapter);
    }
}
