package com.example.pl.testhello.network.model;

import android.content.Context;

import com.example.network.network.BaseModel;
import com.example.network.network.listener.ResponseListener;
import com.example.pl.testhello.network.api.RentOutApi;
import com.example.pl.testhello.network.config.CommonApiEntity;
import com.example.pl.testhello.network.model.bean.ListObjectBean;
import com.example.pl.testhello.network.model.bean.RentOutInfoBean;

import io.reactivex.disposables.Disposable;


/**
 * 房屋出租model
 * Created by pl on 2018/4/28 0028.
 */

public class RentOutModel extends BaseModel {
    public Disposable getData(Context context, String objectId, ResponseListener<RentOutInfoBean> listener) {
        return onResponseListener(context, ((RentOutApi) getRequest(new CommonApiEntity(), RentOutApi.class))
                .getData(objectId), listener);
    }

    public Disposable getDataByCity(Context context, String condition, ResponseListener<ListObjectBean<RentOutInfoBean>> listener) {
        return onResponseListener(context, ((RentOutApi) getRequest(new CommonApiEntity(), RentOutApi.class))
                .getDataByCity(condition), listener);
    }

    /**
     * 分页查询
     * @param context
     * @param pageSize    每页显示的条数
     * @param offsetSize  跳过的条数  currentPage*pageSize
     * @param listener
     * @return
     */
    public Disposable getLimitListData(Context context, int pageSize, int offsetSize, ResponseListener<ListObjectBean<RentOutInfoBean>> listener) {
        return onResponseListener(context, ((RentOutApi) getRequest(new CommonApiEntity(), RentOutApi.class))
                .getLimitListData(pageSize, offsetSize), listener);
    }


    public Disposable getListData(Context context, ResponseListener<ListObjectBean<RentOutInfoBean>> listener) {
        return onResponseListener(context, ((RentOutApi) getRequest(new CommonApiEntity(), RentOutApi.class))
                .getListData(), listener);
    }

    public Disposable postData(Context context, RentOutInfoBean rentOutInfoBean, ResponseListener<RentOutInfoBean> listener) {
        return onResponseListener(context, ((RentOutApi) getRequest(new CommonApiEntity(), RentOutApi.class))
                .postData(rentOutInfoBean), listener);
    }

    public Disposable putData(Context context, String objectId, RentOutInfoBean rentOutInfoBean, ResponseListener<RentOutInfoBean> listener) {
        return onResponseListener(context, ((RentOutApi) getRequest(new CommonApiEntity(), RentOutApi.class))
                .putData(objectId, rentOutInfoBean), listener);
    }

    public Disposable deleteData(Context context, String objectId, ResponseListener<RentOutInfoBean> listener) {
        return onResponseListener(context, ((RentOutApi) getRequest(new CommonApiEntity(), RentOutApi.class))
                .deleteData(objectId), listener);
    }

}
