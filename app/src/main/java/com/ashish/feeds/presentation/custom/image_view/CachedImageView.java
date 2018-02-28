package com.ashish.feeds.presentation.custom.image_view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.ashish.feeds.R;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ashish
 * @since 28/02/18
 */
public class CachedImageView extends AppCompatImageView {

    private static final int INVALID_VALUE = -1;
    private static final float THUMB_VALUE = 0.1f;
    private static final float DEFAULT_OVERLAY = 0.0f;

    private BaseCachedTransformation transformation;
    private BitmapTransformation scaleTransformation;
    private int effect;
    private int square;
    private float cornerRadius;
    private float cornerMargin;
    private int placeHolder;
    private String uri;
    private boolean thumbnail;
    private int animateId;
    private Drawable placeHolderDrawable;
    private int imageSource;

    public CachedImageView(Context context) {
        super(context);
        init(null);
    }

    public CachedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CachedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        Resources.Theme theme = getContext().getTheme();
        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.CachedImageView, 0, 0);
        int effect = a.getInt(R.styleable.CachedImageView_effects, 0);
        int square = a.getInt(R.styleable.CachedImageView_square, Square.NONE);
        float cornerRadius = a.getDimension(R.styleable.CachedImageView_corner_radius, 0);
        float cornerMargin = a.getDimension(R.styleable.CachedImageView_corner_margin, 0);
        int placeHolder = a.getResourceId(R.styleable.CachedImageView_place_holder, INVALID_VALUE);
        boolean thumbnail = a.getBoolean(R.styleable.CachedImageView_thumbnail, true);
        int animationId = a.getResourceId(R.styleable.CachedImageView_animation, INVALID_VALUE);
        int imageSource = a.getResourceId(R.styleable.CachedImageView_source, INVALID_VALUE);
        a.recycle();

        setCornerMargin(cornerMargin);
        setEffect(effect);
        setSquare(square);
        setCornerRadius(cornerRadius);
        setPlaceHolder(placeHolder);
        setThumbnail(thumbnail);
        setAnimateId(animationId);
        setImageSource(imageSource);
    }

    private void updateEffect() {
        if (isInEditMode()) {
            return;
        }
        switch (effect) {
            case Effects.CIRCULAR: {
                transformation = new CircularTransformation(getContext());
                break;
            }
            case Effects.CORNERED: {
                transformation = new CorneredTransformation(getContext(),
                        cornerRadius, cornerMargin);
                break;
            }
            default:
            case Effects.NO_EFFECT: {
                transformation = null;
                break;
            }
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (square == Square.FIXED_WIDTH)
            heightMeasureSpec = widthMeasureSpec;
        else if (square == Square.FIXED_HEIGHT)
            widthMeasureSpec = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(scaleType);
        if (isInEditMode()) {
            return;
        }
        switch (scaleType) {
            case CENTER_CROP:
                scaleTransformation = new CenterCrop(getContext());
                break;
            case FIT_CENTER:
                scaleTransformation = new FitCenter(getContext());
                break;
            default: {
                scaleTransformation = null;
                break;
            }
        }
    }

    public void setAnimateId(int animateId) {
        this.animateId = animateId;
    }

    public void setThumbnail(boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        updateEffect();
    }

    public void setCornerMargin(float cornerMargin) {
        this.cornerMargin = cornerMargin;
        updateEffect();
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
        if (isInEditMode()) {
            setImageResource(placeHolder);
        }
    }

    public void setEffect(int e) {
        if (effect == e) {
            return;
        }
        effect = e;
        updateEffect();
    }

    public void setSquare(int s) {
        square = s;
    }

    public void setPlaceHolderDrawable(Drawable placeHolderDrawable) {
        this.placeHolderDrawable = placeHolderDrawable;
    }

    public void setUri(@NonNull String URI) {
        this.uri = null;

        if (isInEditMode()) {
            setBackgroundColor(Color.BLUE);
            return;
        }
        if (TextUtils.equals(URI, this.uri)) {
            showPlaceHolder();
            return;
        }
        this.uri = URI;
        DrawableTypeRequest<String> requestBuilder = Glide.with(getContext()).load(uri);
        createBuilder(requestBuilder);
    }

    private void showPlaceHolder() {
        if (null != placeHolderDrawable) {
            setImageDrawable(placeHolderDrawable);
        } else if (INVALID_VALUE != placeHolder) {
            setImageResource(placeHolder);
        }
    }

    private void createBuilder(DrawableTypeRequest requestBuilder) {
        // check thumbnail
        if (thumbnail) {
            requestBuilder.thumbnail(THUMB_VALUE);
        }

        if (INVALID_VALUE != placeHolder) {
            requestBuilder.placeholder(placeHolder);
        } else if (null != placeHolderDrawable) {
            requestBuilder.placeholder(placeHolderDrawable);
        }

        // animation
        if (INVALID_VALUE != animateId) {
            requestBuilder.animate(animateId);
        }
        List<BitmapTransformation> list = new ArrayList<>();
        if (null != scaleTransformation) {
            list.add(scaleTransformation);
        }
        if (null != transformation) {
            list.add(transformation);
        }
        if (!list.isEmpty()) {
            BitmapTransformation[] array = new BitmapTransformation[list.size()];
            list.toArray(array);
            requestBuilder.transform(array);
        }
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestBuilder.into(this);
    }

    public void setImageSource(int imageSource) {
        if (this.imageSource == imageSource || INVALID_VALUE == imageSource) {
            return;
        }
        this.imageSource = imageSource;
        if (isInEditMode()) {
            setImageResource(imageSource);
            return;
        }
        DrawableTypeRequest<Integer> requestBuilder = Glide.with(getContext()).load(imageSource);
        createBuilder(requestBuilder);
    }

    public static final class Effects {
        public static final int NO_EFFECT = 0;
        public static final int CIRCULAR = 1;
        public static final int CORNERED = 2;
    }

    public static final class Square {
        public static final int NONE = 0;
        public static final int FIXED_WIDTH = 1;
        public static final int FIXED_HEIGHT = 2;
    }
}

