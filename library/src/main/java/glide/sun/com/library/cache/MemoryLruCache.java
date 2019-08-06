package glide.sun.com.library.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import glide.sun.com.library.BitmapRequest;

public class MemoryLruCache implements BitmapCache {
    private LruCache<String, Bitmap> lruCache;
    private static volatile MemoryLruCache instance;
    private static final byte[] lock = new byte[0];

    public static MemoryLruCache getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new MemoryLruCache();
                }
            }
        }
        return instance;
    }

    private MemoryLruCache() {
        int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 16);
        if (maxMemorySize <= 0) {
            maxMemorySize = 10 * 1024 * 1024;
        }
        lruCache = new LruCache<String, Bitmap>(maxMemorySize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
            // -“张图片的大小
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        if (bitmap!=null)
            lruCache.put(request.getMd5Url(),bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return lruCache.get(request.getMd5Url());
    }

    @Override
    public void remove(BitmapRequest request) {
        lruCache.remove(request.getMd5Url());
    }
}