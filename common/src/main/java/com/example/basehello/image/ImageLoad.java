package com.example.basehello.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * desc:
 * created by pl at 2018/12/20
 */
public class ImageLoad {
    public static void loadImage(Context context, String url, ImageView target, @DrawableRes int drawableId) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(drawableId))
                .into(target);
    }

    public static void loadImage(Context context, String url, ImageView target) {
        Glide.with(context)
                .load(url)
                .into(target);
    }

    public static void loadBlurImage(Context context, String url, ImageView target, int defaultId){
        Glide.with(context)
                .load(url)
                //radius值越大，模糊度越高
                .apply(new RequestOptions().placeholder(defaultId))
                .apply(RequestOptions.bitmapTransform(new GlideRoundTransform(23)))
                .into(target);
    }


    public static void loadImage(Context context, String url, ImageView target, @DrawableRes int drawableId, final ImageLoadListener loadListener) {
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (loadListener != null) {
                            loadListener.onLoadFailed();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (loadListener != null) {
                            loadListener.onLoadSuccess();
                        }
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(drawableId))
                .into(target);
    }

    public interface ImageLoadListener {

        void onLoadSuccess();

        void onLoadFailed();
    }
}
