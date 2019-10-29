package com.moaplanet.gosingadmin.utils;

import android.text.InputFilter;

import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isEmail(String email) {
        Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean isPw(String pw) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]*$");
        return pattern.matcher(pw).matches();
    }

    public static InputFilter notEmptyFilter() {
        return (charSequence, i, i1, spanned, i2, i3) -> {
            if (charSequence.equals(" ")) {
                return "";
            } else {
                return charSequence;
            }
        };
    }

}
