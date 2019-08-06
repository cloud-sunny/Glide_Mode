package glide.sun.com.library;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

import glide.sun.com.library.cache.DoubleLruCache;

/**
 * 加载图片
 */
public class BitmapDispatcher extends Thread {
    //定义队列
    private LinkedBlockingQueue<BitmapRequest> requestQueue;

    private Handler handler;

    private static Application app;

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        super.run();
        while (!interrupted()) {
            try {
                BitmapRequest br = requestQueue.take();
                //设置占位图片
                showPlaceholder(br);
                //下载图片
                Bitmap bitmap = findBitMap(br);
                //加载图片
                showImageView(br, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //设置图片
    private void showImageView(BitmapRequest br, final Bitmap bitmap) {
        if (bitmap != null && br.getImageView() != null && br.getMd5Url().equals(br.getImageView().getTag())) {
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    private DoubleLruCache doubleLruCache = new DoubleLruCache(app);

    //找图片
    private Bitmap findBitMap(BitmapRequest br) {
        Bitmap bitmap = null;
        //先缓存获取
        bitmap = doubleLruCache.get(br);
        //缓存没有进行网络
        if (bitmap == null) {

            bitmap = downLoadBitMap(br.getUrl());
            if (bitmap!=null)
                doubleLruCache.put(br,bitmap);
        }

        if (bitmap == null) {
            br.getRequestListener().onFail();
        } else {
            br.getRequestListener().onSuccess(bitmap);
        }
        return bitmap;
    }

    /**
     * 网络请求图片
     *
     * @param uri
     * @return
     */
    private Bitmap downLoadBitMap(String uri) {
        Bitmap bitmap = null;
        InputStream is = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * 设置占位图
     * @param br
     */
    private void showPlaceholder(BitmapRequest br) {
        if (br.getResId() > 0 && br.getImageView() != null) {
            final int resId = br.getResId();
            final ImageView imageView = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(resId);
                }
            });

        }
    }

    public static void init(Application application){
        app=application;
    }
}
