package com.moaplanet.gosingadmin.utils;

import android.text.InputFilter;

import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isEmail(String email) {
        Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean isPw(String pw) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,!.<>\\/?~`]*$");
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

    /**
     * 핸드폰번호 유효성 체크
     */
    public static boolean isPhoneNumber(String phoneNumber) {

        if (phoneNumber.contains("-")) {
            return Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-(\\d{4})$", phoneNumber);
        } else {
            return Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})(\\d{4})$", phoneNumber);
        }
    }

    /**
     * 특수문자 포함여부 체크
     */
    public static boolean isContainSpecialCharacter(String text) {
        if (text != null && text.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝| ]*")) {
            return false;
        } else {
            return true;
        }
    }

}
