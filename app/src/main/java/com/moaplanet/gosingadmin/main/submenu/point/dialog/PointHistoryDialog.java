package com.moaplanet.gosingadmin.main.submenu.point.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.moaplanet.gosingadmin.R;

public class PointHistoryDialog extends DialogFragment {

    private View view;
    private String type;
    private View.OnClickListener onClickListener;

    public void setType(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_point_history, container);
        initView();
        return view;
    }

    private void initView() {
        TextView dialogDone = view.findViewById(R.id.tv_point_history_dialog_done);
        TextView dialogPoint = view.findViewById(R.id.tv_point_history_dialog_point);

        if (type.equals("deposit")) {
            dialogPoint.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_4300ff_plus,
                    0,
                    0,
                    0);
        } else {
            dialogPoint.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_ff4a24_minus,
                    0,
                    0,
                    0);
        }

        dialogPoint.setText("3,000원");
        dialogDone.setOnClickListener(onClickListener);
        setCancelable(false);
    }

    public void setDialogDoneClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
