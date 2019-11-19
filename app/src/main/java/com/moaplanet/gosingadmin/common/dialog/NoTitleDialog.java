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
    // 아니요, 예 표시 유무
    private boolean typeYesOrNo = false;
    @StringRes
    private int content;
    private String stringContent;
    // 확인 클릭 리스너
    private View.OnClickListener onDonClickListener;
    // 아니요 클릭 리스너
    private View.OnClickListener onNoOnClickListener;

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
        if (stringContent == null) {
            tvContent.setText(getString(content));
        } else {
            tvContent.setText(stringContent);
        }
        TextView tvDone = view.findViewById(R.id.tv_no_title_ok);
        tvDone.setOnClickListener(onDonClickListener);
        setCancelable(false);

        TextView tvNo = view.findViewById(R.id.tv_dialog_no_title_no);
        if (!typeYesOrNo) {
            tvNo.setVisibility(View.GONE);
        } else {
            tvDone.setText(R.string.dialog_no_title_yes);
            tvNo.setOnClickListener(onNoOnClickListener);
        }

    }

    public void setContent(@StringRes int resId) {
        this.content = resId;
    }

    public void setContent(String stringContent) {
        this.stringContent = stringContent;
    }

    /**
     * 확인 버튼 클릭
     */
    public void onDoneOnCliListener(View.OnClickListener onDonClickListener) {
        this.onDonClickListener = onDonClickListener;
    }

    public void onNoOnClickListener(View.OnClickListener onNoOnClickListener) {
        this.onNoOnClickListener = onNoOnClickListener;
    }

    /**
     * 예, 아니요 버튼을 확성화 할지 유무
     * true : 활성화
     * false : 비활성화
     */
    public void setUseYesOrNo(boolean typeYesOrNo) {
        this.typeYesOrNo = typeYesOrNo;
    }

}
