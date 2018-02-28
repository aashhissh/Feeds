package com.ashish.feeds.presentation.custom.image_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/**
 * @author ashish
 * @since 28/02/18
 */
public class CorneredTransformation extends BaseCachedTransformation {

    private final int radius;
    private final int margin;

    public CorneredTransformation(Context context, float radius, float margin) {
        this(Glide.get(context).getBitmapPool(), (int) radius, (int) margin);
    }

    public CorneredTransformation(BitmapPool pool, int radius, int margin) {
        super(pool);
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(new RectF(margin, margin, width - margin, height - margin), radius,
                radius, paint);
        return bitmap;
    }

    @Override
    public String getId() {
        return "RoundedTransformation(radius=" + radius + ", margin=" + margin + ")";
    }
}
