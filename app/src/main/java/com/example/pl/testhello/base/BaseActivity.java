package com.example.pl.testhello.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.basehello.R;
import com.example.network.network.manager.RxManager;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无 MVP 的 Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnBinder;
    private RxManager rxManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxManager = new RxManager();
        ActivityCollector.addActivity(this);
        initWindow();
        setContentView(getLayout());
        EventBus.getDefault().register(this);
        mUnBinder = ButterKnife.bind(this);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.statusBarWhite));
        StatusBarUtil.setLightMode(this);
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyData();
        mUnBinder.unbind();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        ActivityCollector.removeActivity(this);
        rxManager.unSubscribe();
    }

    protected void initWindow() {
    }

    protected abstract int getLayout();

    protected void initData(Bundle savedInstanceState) {
    }

    protected void destroyData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object o) {
    }
}
