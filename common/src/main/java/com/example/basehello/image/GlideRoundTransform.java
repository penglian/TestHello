package com.example.basehello.image;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.internal.FastBlur;

/**
 * desc: 高斯模糊 先充满控件再模糊转换
 * created by pl at 2018/11/28
 */
public class GlideRoundTransform extends CenterCrop {
    private static final int VERSION = 1;
    private static final String ID =
            "jp.wasabeef.glide.transformations.BlurTransformation." + VERSION;

    private static int MAX_RADIUS = 25;
    private static int DEFAULT_DOWN_SAMPLING = 1;

    private int radius;
    private int sampling;

    public GlideRoundTransform() {
        this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public GlideRoundTransform(int radius) {
        this(radius, DEFAULT_DOWN_SAMPLING);
    }

    public GlideRoundTransform(int radius, int sampling) {
        this.radius = radius;
        this.sampling = sampling;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = super.transform(pool, toTransform, outWidth, outHeight);
        bitmap = FastBlur.blur(bitmap, radius, true);
        return bitmap;
    }

    @NonNull
    @Override
    public String toString() {
        return "BlurTransformation(radius=" + radius + ", sampling=" + sampling + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GlideRoundTransform &&
                ((GlideRoundTransform) o).radius == radius &&
                ((GlideRoundTransform) o).sampling == sampling;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + radius * 1000 + sampling * 10;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + radius + sampling).getBytes(CHARSET));
    }
}
