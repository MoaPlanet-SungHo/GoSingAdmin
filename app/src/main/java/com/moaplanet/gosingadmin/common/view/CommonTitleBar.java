package com.moaplanet.gosingadmin.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.moaplanet.gosingadmin.R;

public class CommonTitleBar extends ConstraintLayout {

    private TextView title;
    private View btnBack;

    public CommonTitleBar(Context context) {
        super(context);
        initView();
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttr(attrs);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getAttr(attrs);
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().
                obtainStyledAttributes(attrs, R.styleable.CommonTitleBar);

        String temp = typedArray.getString(R.styleable.CommonTitleBar_title);
        title.setText(temp);

        typedArray.recycle();
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(infService);
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(
                    R.layout.common_title_bar,
                    this,
                    false
            );
        }
        addView(view);

        title = findViewById(R.id.tv_common_title_bar_title);
        btnBack = findViewById(R.id.cl_common_title_bar_back);
    }

    public void setBackButtonClickListener(OnClickListener onClickListener) {
        btnBack.setOnClickListener(onClickListener);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void onHideBack() {
        btnBack.setVisibility(GONE);
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getString(resId));
    }
}
