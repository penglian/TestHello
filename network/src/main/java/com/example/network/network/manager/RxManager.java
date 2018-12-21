package com.example.network.network.manager;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 用于管理Rxjava 注册订阅和取消订阅
 * Created by pl on 2017/9/12.
 */

public class RxManager {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();// 管理订阅者者

    public void register(Disposable object) {
        mCompositeDisposable.add(object);
    }

    public void unSubscribe() {
        mCompositeDisposable.dispose();// 取消订阅
    }
}