package com.example.pl.testhello.base;

import android.os.Bundle;

/**
 * MVP 的 Activity 基类
 */
public abstract class MVPBaseActivity<V extends MVPBaseView, P extends MVPBasePresenter<V>>
        extends BaseActivity implements MVPBaseView {
    private P presenter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter != null) {
            presenter.attachView((V) this);
        }
    }

    @Override
    protected void destroyData() {
        super.destroyData();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

    protected abstract P createPresenter();

    public P getPresenter() {
        return presenter;
    }
}
