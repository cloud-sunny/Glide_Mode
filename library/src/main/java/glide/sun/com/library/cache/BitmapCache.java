package glide.sun.com.library.cache;

import android.graphics.Bitmap;

import glide.sun.com.library.BitmapRequest;

public interface BitmapCache {
    /**
     * 入内存
     *
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 读取缓存的图片
     *
     * @param request
     */
    Bitmap get(BitmapRequest request);

    /**
     * 清除缓你的图片
     *
     * @param request
     */
    void remove(BitmapRequest request);
}

