package com.example.pl.testhello.test.scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.basehello.utils.PermissionUtil;
import com.example.basehello.utils.ToastUtil;
import com.example.pl.testhello.R;
import com.example.pl.testhello.base.BaseActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.OnClick;

/**
 * desc:
 * created by pl at 2018/12/29
 */
public class ScanMainActivity extends BaseActivity {
    private String[] permissionArr = {"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    @Override
    protected int getLayout() {
        return R.layout.activity_scan_main;
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onClick(View view) {
        if(view.getId() == R.id.button4){
            startActivity(new Intent(this, FourActivity.class));
        }else if(view.getId() == R.id.button2){
            goToPicture();
        }else if(view.getId() == R.id.button1){
            goToSimpleCapture();
        }else if(view.getId() == R.id.button3){
            goToCustomCapture();
        }
    }

    private void goToSimpleCapture() {
        PermissionUtil.requestPermission(this, new PermissionUtil.PermissionListener() {
            @Override
            public void onGranted() {
                Intent intent = new Intent(ScanMainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }

            @Override
            public void onDenied() {
                ToastUtil.showLong(ScanMainActivity.this, "权限以拒绝");
            }
        }, "android.permission.CAMERA");
    }

    private void goToCustomCapture() {
        PermissionUtil.requestPermission(this, new PermissionUtil.PermissionListener() {
            @Override
            public void onGranted() {
                Intent intent = new Intent(ScanMainActivity.this, ThreeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }

            @Override
            public void onDenied() {
                ToastUtil.showLong(ScanMainActivity.this, "权限以拒绝");
            }
        }, "android.permission.CAMERA");
    }

    private void goToPicture() {
        PermissionUtil.requestPermission(this, new PermissionUtil.PermissionListener() {
            @Override
            public void onGranted() {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }

            @Override
            public void onDenied() {
                ToastUtil.showLong(ScanMainActivity.this, "权限以拒绝");
            }
        }, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(ScanMainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }

        /**
         * 选择系统图片并解析
         */
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(ScanMainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ScanMainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
