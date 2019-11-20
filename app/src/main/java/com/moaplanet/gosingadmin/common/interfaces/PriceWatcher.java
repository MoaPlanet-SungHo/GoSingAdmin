package com.moaplanet.gosingadmin.common.interfaces;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.utils.StringUtil;

/**
 * 가격 입력 관련 watcher
 */
public class PriceWatcher implements TextWatcher {

    // EditText
    private EditText mEditText;
    // 처음 입력 받은건지 아닌지에대한 플래그 값
    // true : 처음 입력받음 | false : 처음 입력받은게 아님
    private boolean mIsFirstInput = true;

    // 콜백 리스터
    private onPriceWatcherCallback mCallback;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        mEditText.removeTextChangedListener(this);
        String inputText = editable.toString().replace("원", "");

        int price;
        try {
            price = Integer.parseInt(inputText.replaceAll(",", ""));
        } catch (NumberFormatException e) {
            price = 0;
        }

        int wonLen;
        if (mIsFirstInput) {
            wonLen = -1;
        } else {
            wonLen = 0;
        }
        mIsFirstInput = false;

        int startSelectionPos = mEditText.getSelectionStart();
        int beforeLength = editable.length();

        Context context = mEditText.getContext();
        String changePrice = context.getString(
                R.string.common_price_won,
                StringUtil.convertCommaPrice(price));

        mEditText.setText(changePrice);

        int afterLength = mEditText.getText().length();
        int selectionPos = (startSelectionPos + (afterLength - beforeLength)) + wonLen;
        if (selectionPos >= 0) {
            mEditText.setSelection(selectionPos);
        } else {
            mEditText.setSelection(0);
        }

        mEditText.addTextChangedListener(this);
        mCallback.onTextChange(changePrice, price);
    }

    public PriceWatcher(EditText editText) {
        this.mEditText = editText;
    }

    public void setCallback(onPriceWatcherCallback callback) {
        this.mCallback = callback;
    }

    public interface onPriceWatcherCallback {
        /**
         * 텍스트 변환후 콜백 리스트
         *
         * @param completePrice 변경된 텍스트
         * @param price         가격
         */
        void onTextChange(String completePrice, int price);
    }


}
