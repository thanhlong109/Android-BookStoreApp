package com.group2.bookstoreproject.util;

import android.content.res.Resources;

public class ScreenUtil {

    public static int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}

