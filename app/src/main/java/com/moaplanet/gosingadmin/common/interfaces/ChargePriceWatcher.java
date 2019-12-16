package com.moaplanet.gosingadmin.common.interfaces;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.moaplanet.gosingadmin.R;
import com.moaplanet.gosingadmin.utils.StringUtil;
import com.orhanobut.logger.Logger;

/**
 * 가격 입력 관련 watcher
 */
public class ChargePriceWatcher implements TextWatcher {

    // EditText
    private EditText mEditText;
    // 처음 입력 받은건지 아닌지에대한 플래그 값
    // true : 처음 입력받음 | false : 처음 입력받은게 아님
    private boolean mIsFirstInput = true;

    // 콜백 리스터
    private onPriceWatcherCallback mCallback;

    // 현재 입력된 택스트의 길이
    private int beforeInputLength = 0;
    // 현재 입력하는 커서 포지션
    private int inputPos = 0;
    // 현재 입력된 값
    private int beforePrice = 0;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        beforeInputLength = charSequence.length();
        inputPos = i;

        try {
            beforePrice = Integer.parseInt(charSequence.toString().replaceAll("[,원]", ""));
        } catch (NumberFormatException e) {
            beforePrice = 0;
        }

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

        mEditText.removeTextChangedListener(this);
        // 숫자 삭제 유무
        boolean isRemove = editable.length() < beforeInputLength;
        String inputText = editable.toString().replace("원", "");

        int price;


        try {
            price = Integer.parseInt(inputText.replaceAll(",", ""));
        } catch (NumberFormatException e) {
            price = 0;
        }

        if (!isRemove) {
            if (price % 1000 != 0 || (beforeInputLength - inputPos) <= 4) {
                int inputPr = Character.getNumericValue(editable.charAt(inputPos));
                int tempInputPr = inputPr * 1000;
                if (tempInputPr == 0) {
                    price = beforePrice * 10;
                } else {
                    price = (beforePrice * 10) + tempInputPr;
                }
            }
        } else {
            if (price < 1000) {
                price = 0;
            } else if (price % 1000 != 0) {
                price = price - (price % 1000);
            }
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

    public ChargePriceWatcher(EditText editText) {
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
