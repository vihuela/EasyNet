package com.hadlink.easynet.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * @author YaoWeihui on 2016/5/9.
 */
public class Tools {
    static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable();
    }
}
