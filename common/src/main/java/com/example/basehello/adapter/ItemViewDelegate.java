package com.example.basehello.adapter;

public interface ItemViewDelegate<T> {

    int getLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
