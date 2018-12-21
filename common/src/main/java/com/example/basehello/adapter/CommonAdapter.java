package com.example.basehello.adapter;

import android.content.Context;

import java.util.List;

public class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;

    public CommonAdapter(Context context, List datas) {
        super(context, datas);
    }
}
