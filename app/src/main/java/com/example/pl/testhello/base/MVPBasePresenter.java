package com.example.pl.testhello.base;

/**
 * MVP 的 P 基类
 */
public abstract class MVPBasePresenter<V extends MVPBaseView> {
    private V mView;

    public void attachView(V view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    public V getView() {
        return mView;
    }

}
