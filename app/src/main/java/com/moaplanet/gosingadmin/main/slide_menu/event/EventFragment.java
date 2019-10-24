package com.moaplanet.gosingadmin.main.slide_menu.event;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.slide_menu.event.adapter.EventAdapter;

public class EventFragment extends BaseFragment {
    private RecyclerView rvEvent;

    @Override
    public int layoutRes() {
        return R.layout.fragment_event;
    }

    @Override
    public void initView(View view) {
        rvEvent = view.findViewById(R.id.rv_event_list);
        rvEvent.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventAdapter eventAdapter = new EventAdapter();
        rvEvent.setAdapter(eventAdapter);
    }
}
