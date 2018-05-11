package com.bangqu.lib.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by Hello on 2015/8/21.
 * 下载图片转换圆角图片的方法
 * .transform(new GlideRoundTransform(context, 10))
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static Bitmap.Config config = Bitmap.Config.ARGB_8888;

    private static float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform, outWidth, outHeight);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        if (source == null) return null;
        //图片缩放
        Matrix matrix = new Matrix();
        float scaleX = (float) outWidth / source.getWidth();
        float scaleY = (float) outHeight / source.getHeight();
        matrix.postScale(scaleX, scaleY);
        //grk __begin
        //grk___end
        //绘制图形
        Bitmap result = pool.get(outWidth, outHeight, config);
        if (result == null) {
            result = Bitmap.createBitmap(outWidth, outHeight, config);
        }
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, outWidth, outHeight);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
