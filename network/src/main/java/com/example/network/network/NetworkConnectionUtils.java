package com.example.network.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.util.List;


/**
 * Created by pl on 2017/6/12.
 * <p>
 * Wifi连接工具类
 */
public class NetworkConnectionUtils {
    private final static String TAG = "NetworkConnectionUtils";

    public NetworkConnectionUtils() {
    }

    /**
     * 获取要连接的wifi节点各个配置选项的加密类型
     *
     * @param ssid
     * @return wifiConfiguration
     */
    public static WifiConfiguration getWifiConfiguration(WifiManager manager, String ssid, String
            password) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + ssid + "\"";

        List<ScanResult> list = manager.getScanResults();
        for (ScanResult scResult : list) {
            if (ssid.equals(scResult.SSID)) {
                String capabilities = scResult.capabilities;
                if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
                    wifiConfiguration.preSharedKey = "\"" + password + "\"";
                } else if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
                    wifiConfiguration.preSharedKey = "\"" + password + "\"";
                } else {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE);
                }
            }
        }
        return wifiConfiguration;
    }

    /**
     * 格式化RouterSSID
     *
     * @param strRouterSSID 要格式化的当前连接的路由ssid
     * @return 去除"\"后的RouterSSID字符串
     */
    public static String formatRouterSSID(String strRouterSSID) {
        //e("formate routerSSID before---" + strRouterSSID);
        if (strRouterSSID.contains("\"")) {
            strRouterSSID = strRouterSSID.replaceAll("\"", "");
            //e("formate routerSSID after---" + strRouterSSID);
        }
        return strRouterSSID;
    }

    /**
     * Ping
     * 用于确定手机是否已经连接上指定设备ip地址
     */
    public static boolean pingTest(String IPOrDomainName) {

        boolean isSuccess = false;
        int status;
        String result = "failed";
        Process p;
        try {
            p = Runtime.getRuntime().exec("ping -c 1 " + IPOrDomainName);//
            // m_strForNetAddress是输入的网址或者Ip地址
            status = p.waitFor();// status 只能获取是否成功，无法获取更多的信息

            if (status == 0) {
                result = "success";
                isSuccess = true;
            }

        } catch (IOException | InterruptedException e) {

        }
        return isSuccess;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info && info.isConnected()) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有网络
     *
     * @return 返回值
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;

    }


    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction(Intent.ACTION_VIEW);
        activity.startActivityForResult(intent, requestCode);
    }
}
