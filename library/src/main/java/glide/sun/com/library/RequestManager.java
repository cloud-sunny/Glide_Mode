package glide.sun.com.library;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class RequestManager {

    private static RequestManager manager = new RequestManager();

    //定义线程数组
    private BitmapDispatcher[] bitmapDispatcher;

    //定义队列
    private LinkedBlockingQueue<BitmapRequest> requtstQueue = new LinkedBlockingQueue<>();

    public void addBitmapRequest(BitmapRequest br) {
        if (br == null)
            return;
        if (!requtstQueue.contains(br))
            requtstQueue.add(br);
    }

    private void start() {
        stop();
        startAllDispacher();
    }

    private void stop() {
        if (bitmapDispatcher != null && bitmapDispatcher.length > 0) {
            for (int i = 0; i < bitmapDispatcher.length; i++) {
                if (!bitmapDispatcher[i].isInterrupted()) {
                    bitmapDispatcher[i].isInterrupted();
                }
            }
        }
    }

    private void startAllDispacher() {
        //获取手机手机设备单App最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatcher = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requtstQueue);
            bitmapDispatcher.start();
            //将bitmapDispacher添加到数组中方便管理
            this.bitmapDispatcher[i] = bitmapDispatcher;
        }
    }

    private RequestManager() {
        start();
    }

    public static RequestManager getInstance() {
        return manager;
    }
}
