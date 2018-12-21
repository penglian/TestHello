package com.example.pl.testhello.base;

/**
 * MVP 的 Fragment 基类
 */
public abstract class MVPBaseFragment<V extends MVPBaseView, P extends MVPBasePresenter<V>>
        extends BaseFragment implements MVPBaseView {
    private P presenter;

    @Override
    protected void initData() {
        super.initData();
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
