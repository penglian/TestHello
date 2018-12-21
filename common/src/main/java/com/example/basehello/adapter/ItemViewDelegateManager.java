package com.example.basehello.adapter;

import android.util.SparseArray;

public class ItemViewDelegateManager<T> {
    SparseArray<ItemViewDelegate<T>> delegates = new SparseArray<>();

    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    public ItemViewDelegateManager addDelegate(ItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
        }
        return this;
    }

    public ItemViewDelegateManager addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException("ItemViewDelegate had already registered, viewType = " + viewType
                    + "ItemViewDelegate is " + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegate getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    public int getItemViewType(T item, int position) {
        int count = delegates.size();
        for (int i = count - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegate matched, position = " + position);
    }

    public void convert(ViewHolder holder, T item, int position) {
        int count = delegates.size();
        for (int i = 0; i < count; i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException("No ItemViewDelegate matched, position = " + position);
    }


}
