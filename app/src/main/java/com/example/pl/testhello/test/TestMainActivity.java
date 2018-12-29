package com.example.pl.testhello.test;

import android.content.Intent;
import android.view.View;

import com.example.basehello.utils.PermissionUtil;
import com.example.basehello.utils.ToastUtil;
import com.example.pl.testhello.R;
import com.example.pl.testhello.base.BaseActivity;
import com.example.pl.testhello.test.scan.ScanMainActivity;

import butterknife.OnClick;

/**
 * desc:
 * created by pl at 2018/12/29
 */
public class TestMainActivity extends BaseActivity {
    private String[] permissionArr = {"android.permission.ACCESS_FINE_LOCATION"};

    @Override
    protected int getLayout() {
        return R.layout.activity_test_main;
    }

    @OnClick({R.id.btn_test_network, R.id.btn_greendao, R.id.btn_location, R.id.btn_scan})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_test_network:
                startActivity(new Intent(this, TestNetworkActivity.class));
                break;
            case R.id.btn_greendao:
                startActivity(new Intent(this, TestGreendao.class));
                break;
            case R.id.btn_location:
                PermissionUtil.requestPermission(this, new PermissionUtil.PermissionListener() {
                    @Override
                    public void onGranted() {
                        startActivity(new Intent(TestMainActivity.this, TestLocatioActitity.class));
                    }

                    @Override
                    public void onDenied() {
                        ToastUtil.showLong(TestMainActivity.this, "权限以拒绝");
                    }
                }, permissionArr);
                break;
            case R.id.btn_scan:
                startActivity(new Intent(this, ScanMainActivity.class));
                break;
        }
    }
}
