package com.moaplanet.gosingadmin.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 디바이스 관련 유틸
 */
public class DeviceUtil {

    public static int getScreenWith(Context context) {
        if (context == null) {
            return 0;
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (wm == null) {
            return 0;
        }
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;

    }

}
