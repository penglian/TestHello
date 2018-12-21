package com.example.basehello.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;
import java.util.Set;

/**
 * 偏好设置工具类
 */
public class SPUtils {
    private static String NAME = "default_sp";
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static void save(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void save(Context context, String key, float value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void save(Context context, String key, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        editor.apply();
    }

    public static void save(Context context, String key, long value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        editor.apply();
    }

    public static void save(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.apply();
    }

    public static void save(Context context, String key, Set<String> value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(key);
        editor.apply();
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static float getFloat(Context context, String key, float defValue) {
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    public static long getLong(Context context,String key, long defValue) {
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static Set<String> getStringSet(Context context,String key,
                                           Set<String> defValue) {
        return getSharedPreferences(context).getStringSet(key, defValue);
    }

    public static Map<String, ?> getAll(Context context) {
        return getSharedPreferences(context).getAll();
    }

    public static synchronized void save(Context context, String key, Object value) {
        if (value != null) {
            save(context, key, ObjUtils.obj2Json(value));
        }
    }

    public static synchronized Object getObject(Context context, String key, Class<?> cls) {
        String s = getString(context, key, "");
        if (!TextUtils.isEmpty(s)) {
            return ObjUtils.json2Obj(s, cls);
        }
        return null;
    }

}
