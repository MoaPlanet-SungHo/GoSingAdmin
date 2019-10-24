package com.moaplanet.gosingadmin.common.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

import com.moaplanet.gosingadmin.R;

public class NoTitleDialog extends DialogFragment {

    private View view;
    @StringRes
    private int content;
    private View.OnClickListener onClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_no_title, container);
        initView();
        return view;
    }

    private void initView() {
        TextView tvContent = view.findViewById(R.id.tv_no_title_content);
        tvContent.setText(getString(content));
        TextView tvDone = view.findViewById(R.id.tv_no_title_ok);
        tvDone.setOnClickListener(onClickListener);
        setCancelable(false);
    }

    public void setContent(@StringRes int resId) {
        this.content = resId;
    }

    public void onDoneOnCliListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
