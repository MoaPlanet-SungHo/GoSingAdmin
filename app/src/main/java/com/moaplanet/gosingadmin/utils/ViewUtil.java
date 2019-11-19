package com.moaplanet.gosingadmin.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;

import java.util.Objects;

public class ViewUtil {

    public static View getHolderView(ViewGroup viewGroup, @LayoutRes int layoutId) {
        return LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutId, viewGroup, false);
    }

    /**
     * 키보드 내리기
     */
    public static void onHideKeyboard(View view) {
        view.clearFocus();
        //키보드 내리기
        InputMethodManager imm = (InputMethodManager) Objects
                .requireNonNull(view.getContext())
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
