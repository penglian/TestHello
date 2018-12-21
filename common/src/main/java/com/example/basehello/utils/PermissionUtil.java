package com.example.basehello.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class PermissionUtil {

    public static void requestPermission(final Context context, final PermissionListener permissionListener, String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        permissionListener.onGranted();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        permissionListener.onDenied();
                    }
                })
                .start();
    }

    public interface PermissionListener {
        void onGranted();
        void onDenied();
    }
}
