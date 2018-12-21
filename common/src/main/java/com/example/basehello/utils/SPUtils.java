package com.example.basehello.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * 偏好设置工具类
 */
public class SPUtils {

    private static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context, String name) {
        return getSharedPreferences(context, name).edit();
    }

    public static void save(Context context, String name, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void save(Context context, String name, String key, float value) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void save(Context context, String name, String key, int value) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.putInt(key, value);
        editor.apply();
    }

    public static void save(Context context, String name, String key, long value) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.putLong(key, value);
        editor.apply();
    }

    public static void save(Context context, String name, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.putString(key, value);
        editor.apply();
    }

    public static void save(Context context, String name, String key, Set<String> value) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static void remove(Context context, String name, String key) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.remove(key);
        editor.apply();
    }

    public static void clear(Context context, String name) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.clear();
        editor.apply();
    }

    public static boolean getBoolean(Context context, String name, String key, boolean defValue) {
        return getSharedPreferences(context, name).getBoolean(key, defValue);
    }

    public static float getFloat(Context context, String name, String key, float defValue) {
        return getSharedPreferences(context, name).getFloat(key, defValue);
    }

    public static int getInt(Context context, String name, String key, int defValue) {
        return getSharedPreferences(context, name).getInt(key, defValue);
    }

    public static long getLong(Context context, String name, String key, long defValue) {
        return getSharedPreferences(context, name).getLong(key, defValue);
    }

    public static String getString(Context context, String name, String key, String defValue) {
        return getSharedPreferences(context, name).getString(key, defValue);
    }

    public static Set<String> getStringSet(Context context, String name, String key,
                                           Set<String> defValue) {
        return getSharedPreferences(context, name).getStringSet(key, defValue);
    }

    public static Map<String, ?> getAll(Context context, String name) {
        return getSharedPreferences(context, name).getAll();
    }

}
