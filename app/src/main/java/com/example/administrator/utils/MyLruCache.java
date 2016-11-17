package com.example.administrator.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Administrator on 2016/11/16.
 */

public class MyLruCache extends LruCache<String,Bitmap> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */

    public MyLruCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes()*value.getHeight();
    }
}
