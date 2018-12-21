package com.example.basehello.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import java.io.File;

/**
 * 系统工具类
 */
public class SystemUtil {
    private static final String TAG = "SystemUtil";

    private static String mVersionName = "";
    private static int mVersionCode = 0;

    /**
     * 获取应用版本名
     */
    public static String getVersionName(Context context) {
        if (TextUtils.isEmpty(mVersionName)) {
            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                mVersionName = info.versionName;
            } catch (Exception e) {
            }
        }
        return mVersionName;
    }

    /**
     * 获取应用版本号
     */
    public static int getVersionCode(Context context) {
        if (mVersionCode == 0) {
            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                mVersionCode = info.versionCode;
            } catch (Exception e) {
            }
        }
        return mVersionCode;
    }

    /**
     * 关闭输入法
     */
    public static void closeInputMethod(Activity activity) {
        try {
            InputMethodManager manager = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    /**
     *  安装新版本
     * @param apkPath
     * @param activity
     */
    public static void installAPK(String apkPath, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, "com.sixgod.bookshare.fileProvider", new File(apkPath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }

    /**
     * 获取屏幕宽度
     * @param mContext
     * @return
     */
    public static int getWindowWidth(Context mContext){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param mContext
     * @return
     */
    public static int getWindowHeight(Context mContext){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

}
