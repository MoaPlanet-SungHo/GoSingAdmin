package com.moaplanet.gosingadmin.common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public abstract class BaseFragment extends Fragment {
    public View view;

    public abstract int layoutRes();

    public abstract void initView(View view);

    public abstract void initListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layoutRes(), container, false);
        initView(view);
        initListener();
        return view;
    }

    protected void onBackNavigation() {
        Navigation.findNavController(view).popBackStack();
    }

    protected void onMoveNavigation(@IdRes int actionId) {
        Navigation.findNavController(view).navigate(actionId);
    }

}
