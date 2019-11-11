package com.moaplanet.gosingadmin.utils;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtil {

    public static SpannableStringBuilder convertFontColor(String str, int color) {
        SpannableStringBuilder sp = new SpannableStringBuilder(str);
        sp.setSpan(new ForegroundColorSpan(color), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

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

    public static InputFilter notKoreanFilter() {
        return (charSequence, i, i1, spanned, i2, i3) -> {

            Pattern pattern = Pattern.compile("^[ㄱ-ㅎ가-힣]*$");
            if (pattern.matcher(charSequence).matches()) {
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

    public static String replaceText(String text, String regex, String replace) {
        return text.replaceAll(regex, replace);
    }

    /**
     * @param convertPrice 단위 표시 해줄 가격
     * @return 가격을 단위를 표시해서 반환
     */
    public static String convertCommaPrice(int convertPrice) {
        return String.format(Locale.getDefault(), "%,d", convertPrice);
    }

}
