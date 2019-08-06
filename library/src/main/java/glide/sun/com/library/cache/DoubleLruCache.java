package glide.sun.com.library.cache;

import android.content.Context;
import android.graphics.Bitmap;

import glide.sun.com.library.BitmapRequest;

public class DoubleLruCache implements BitmapCache {
    //内存
    private MemoryLruCache lruCache;
    //磁盘
    private DiskBitmapCache bitmapCache;

    public DoubleLruCache(Context context) {
        bitmapCache = DiskBitmapCache.getInstance(context);
        lruCache = MemoryLruCache.getInstance();
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        lruCache.put(request, bitmap);
        bitmapCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap = lruCache.get(request);
        if (bitmap == null) {
            bitmap = bitmapCache.get(request);
            lruCache.put(request, bitmap);
        }
        return bitmap;

    }

    @Override
    public void remove(BitmapRequest request) {
        lruCache.remove(request);
        bitmapCache.remove(request);
    }
}