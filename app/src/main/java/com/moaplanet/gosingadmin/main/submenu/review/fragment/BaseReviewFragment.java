package com.moaplanet.gosingadmin.main.submenu.review.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.common.fragment.BaseFragment;
import com.moaplanet.gosingadmin.main.submenu.review.adapter.ReviewAdapter;

public abstract class BaseReviewFragment extends BaseFragment {

    public abstract void init();

    public ReviewAdapter reviewAdapter;

    @Override
    public int layoutRes() {
        return R.layout.fragment_base_review;
    }

    @Override
    public void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_base_review);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        reviewAdapter = new ReviewAdapter();
        recyclerView.setAdapter(reviewAdapter);
    }

    @Override
    public void initListener() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        return view;
    }

}
