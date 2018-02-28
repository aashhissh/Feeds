package com.ashish.feeds.presentation.ui.custom_views.image_view;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @author ashish
 * @since 28/02/18
 */
public abstract class BaseCachedTransformation extends BitmapTransformation {

    public BaseCachedTransformation(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public BaseCachedTransformation(BitmapPool pool) {
        super(pool);
    }

    @Override
    public String getId() {
        return null;
    }
}