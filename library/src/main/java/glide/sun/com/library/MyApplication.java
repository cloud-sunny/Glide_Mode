package glide.sun.com.library;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BitmapDispatcher.init(this);
    }
}
