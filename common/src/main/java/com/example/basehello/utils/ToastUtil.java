package com.example.basehello.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类
 */
public class ToastUtil {
    public static void showShort(Context context, String info) {
        if (context != null) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void showLong(Context context, String info) {
        if (context != null) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, info, Toast.LENGTH_LONG).show();
            }
        }
    }
}
