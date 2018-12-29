package com.example.pl.testhello.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.network.network.listener.ResponseListener;
import com.example.network.network.manager.RxManager;
import com.example.pl.testhello.R;
import com.example.pl.testhello.network.model.RentOutModel;
import com.example.pl.testhello.network.model.bean.ListObjectBean;
import com.example.pl.testhello.network.model.bean.RentOutInfoBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * 网络测试demo类
 */
public class TestNetworkActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnGet;
    Button btnPost;
    Button btnPut;
    Button btnDelete;
    Button btnGetList;
    Button btnGetByWhere;
    TextView tvResult;
    RxManager rxManager;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_connecttion);
//        checkPermissions();
        initView();
        initData();
    }

    private void checkPermissions() {
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );

        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            return;
        }

        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (!isAllGranted) {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                Toast.makeText(TestNetworkActivity.this, "请重新授权", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initData() {
        rxManager = new RxManager();
    }

    private void initView() {
        btnGet = findViewById(R.id.btn_get);
        btnPost = findViewById(R.id.btn_post);
        btnPut = findViewById(R.id.btn_put);
        btnDelete = findViewById(R.id.btn_delete);
        btnGetList = findViewById(R.id.btn_get_list);
        tvResult = findViewById(R.id.tv_result);
        btnGetByWhere = findViewById(R.id.btn_get_by_where);

        btnGet.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnPut.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnGetList.setOnClickListener(this);
        btnGetByWhere.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String objectId = "52396a5b6d";
        RentOutModel rentOutModel = new RentOutModel();
        switch (v.getId()) {
            case R.id.btn_get:
                rxManager.register(rentOutModel.getData(this, objectId, new ResponseListener<RentOutInfoBean>() {
                    @Override
                    public void onSuccess(RentOutInfoBean rentOutInfoBean) {
                        if (rentOutInfoBean != null) {
                            tvResult.setText(rentOutInfoBean.getCity() + ":" + rentOutInfoBean.getContent() + "\n");
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        tvResult.setText(throwable.toString());
                    }
                }));


                break;
            case R.id.btn_get_list:
                //分页查询  根据每次查询到的条数是否为0来判断是否有下一页
                rxManager.register(rentOutModel.getLimitListData(this, 10, 12, new ResponseListener<ListObjectBean<RentOutInfoBean>>() {
                    @Override
                    public void onSuccess(ListObjectBean<RentOutInfoBean> listObjectBean) {
                        if (listObjectBean != null) {
                            List<RentOutInfoBean> list = listObjectBean.getResults();
                            StringBuilder sb = new StringBuilder();
                            for (RentOutInfoBean bean : list) {
                                sb.append(bean.getCity()).append(":").append(bean.getContent()).append("\n");
                            }
                            tvResult.setText(sb.toString());
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        tvResult.setText(throwable.toString());
                    }
                }));

                break;
            case R.id.btn_post:
                RentOutInfoBean testBean = new RentOutInfoBean();
                testBean.setCity("123213");
                testBean.setContent("21312");
                testBean.setFishpond("123123");
                rxManager.register(rentOutModel.postData(this, testBean, new ResponseListener<RentOutInfoBean>() {
                    @Override
                    public void onSuccess(RentOutInfoBean rentOutInfoBean) {
                        if (rentOutInfoBean != null) {
                            //返回一个创建时间与objectId
                            tvResult.setText(rentOutInfoBean.getContent());
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        tvResult.setText(throwable.toString());
                    }
                }));
                break;
            case R.id.btn_put:
                //根据objectId更新
                RentOutInfoBean testBean1 = new RentOutInfoBean();
                testBean1.setCity("新加坡11111111111");
                rxManager.register(rentOutModel.putData(this, objectId, testBean1, new ResponseListener<RentOutInfoBean>() {
                    @Override
                    public void onSuccess(RentOutInfoBean rentOutInfoBean) {
                        if (rentOutInfoBean != null) {
                            //返回修改时间
                            tvResult.setText(rentOutInfoBean.getCity());
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        tvResult.setText(throwable.toString());
                    }
                }));
                break;
            case R.id.btn_delete:
                //根据objectId删除
                rxManager.register(rentOutModel.deleteData(this, objectId, new ResponseListener<RentOutInfoBean>() {
                    @Override
                    public void onSuccess(RentOutInfoBean rentOutInfoBean) {
                        if (rentOutInfoBean != null) {
                            //返回msg  msg为"ok"
                            tvResult.setText("ok");
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        tvResult.setText(throwable.toString());
                    }
                }));
                break;
            case R.id.btn_get_by_where:
                //条件查询  具体结合api文档参照已下的例子
                //如果是字段查询可以用JSON字符串进行传参,主要就是对json字符串的拼接
                Map<String, String> map = new HashMap<>();
                map.put("city", "杭州");
                map.put("content", "9999999999");
                Gson gson = new Gson();
                String condition = gson.toJson(map);
                rxManager.register(rentOutModel.getDataByCity(this, condition, new ResponseListener<ListObjectBean<RentOutInfoBean>>() {
                    @Override
                    public void onSuccess(ListObjectBean<RentOutInfoBean> bean) {
                        if (bean != null) {
                            StringBuilder sb = new StringBuilder();
                            for (RentOutInfoBean rentOutInfoBean : bean.getResults()) {
                                sb.append(rentOutInfoBean.getCity()).append(":").append(rentOutInfoBean.getContent()).append("\n");
                            }
                            tvResult.setText(sb.toString());
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        tvResult.setText(throwable.toString());
                    }
                }));
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxManager.unSubscribe();
    }
}
