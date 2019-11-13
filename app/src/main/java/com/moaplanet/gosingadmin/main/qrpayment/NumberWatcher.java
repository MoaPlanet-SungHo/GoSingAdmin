package com.moaplanet.gosingadmin.main.qrpayment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.widget.EditText;

import com.moaplanet.gosingadmin.utils.StringUtil;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by jiwun on 2019-11-12.
 */
public class NumberWatcher implements TextWatcher {

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;

    private EditText et;

    private boolean tes = false;

    public NumberWatcher(EditText et)
    {
        df = new DecimalFormat("#,###.##");
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        this.et = et;
        hasFractionalPart = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        if (charSequence.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
//        {
//            hasFractionalPart = true;
//        } else {
//            hasFractionalPart = false;
//        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {


//        et.removeTextChangedListener(this);

//        try {
//            int inilen, endlen;
//            inilen = et.getText().length();
//
//            String v = editable.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
//            hasFractionalPart = v.contains(".");
//            Number n = df.parse(v);
//            int cp = et.getSelectionStart();
//            if (!hasFractionalPart) {
//                et.setText(dfnd.format(n));
//            }
//
//            endlen = et.getText().length();
//            int sel = (cp + (endlen - inilen));
//            if (sel > 0 && sel <= et.getText().length()) {
//                et.setSelection(sel);
//            } else {
//                // place cursor at the end?
//                et.setSelection(et.getText().length() - 1);
//            }
//        } catch (NumberFormatException nfe) {
//            // do nothing?
//        } catch (ParseException e) {
//            // do nothing?
//        }
//
//        et.addTextChangedListener(this);



        et.removeTextChangedListener(this);

        try {
            int inilen, endlen;
            inilen = et.getText().length();

            String v = editable.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
            Number n = df.parse(v);
            int cp = et.getSelectionStart();

            if (hasFractionalPart) {
                et.setText(df.format(n));
            } else {
                et.setText(dfnd.format(n));
            }

//            String tt = editable.toString().replaceAll(",", "");
//            et.setText(StringUtil.convertCommaPrice(tt));



            endlen = et.getText().length();
            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= et.getText().length()) {
                et.setSelection(sel);
            } else {
                // place cursor at the end?
                et.setSelection(et.getText().length() - 1);
            }
        } catch (NumberFormatException nfe) {
            // do nothing?
        } catch (ParseException e) {
            // do nothing?
        }
//        TextKeyListener.clear(et.getText());
        et.addTextChangedListener(this);
    }
}
