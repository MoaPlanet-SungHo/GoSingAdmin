package com.moaplanet.gosingadmin.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.moaplanet.gosingadmin.R;

/**
 * 타이틀 없는 다이얼로그
 */
public class CommonTitleNoUnderlineDialog extends Dialog {

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvCancel;
    private TextView tvDone;

    private String title;
    private String content;

    public CommonTitleNoUnderlineDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.dialog_common_title_no_underline);
        initView();
    }

    private void initView() {
        if (this.getWindow() != null) {
            this.getWindow().setBackgroundDrawableResource(R.color.color_00000000);
        }

        tvTitle = findViewById(R.id.tv_dialog_title_no_underline_title);
        tvContent = findViewById(R.id.tv_dialog_title_no_underline_content);
        tvCancel = findViewById(R.id.tv_dialog_title_no_underline_cancel);
        tvDone = findViewById(R.id.tv_dialog_title_no_underline_done);

        tvTitle.setVisibility(View.GONE);
        tvContent.setVisibility(View.GONE);
        tvCancel.setVisibility(View.GONE);
        tvDone.setVisibility(View.GONE);

        if (title != null && !title.isEmpty()) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }

        if (content != null && !content.isEmpty()) {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(content);
        }
    }

    public void setCancelClickListener(View.OnClickListener onClickListener) {
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setOnClickListener(onClickListener);
    }

    public void setDoneClickListener(View.OnClickListener onClickListener) {
        tvDone.setVisibility(View.VISIBLE);
        tvDone.setOnClickListener(onClickListener);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
