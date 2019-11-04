package com.moaplanet.gosingadmin.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

public class ViewUtil {

    public static View getHolderView(ViewGroup viewGroup, @LayoutRes int layoutId) {
        return LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutId, viewGroup, false);
    }

}
