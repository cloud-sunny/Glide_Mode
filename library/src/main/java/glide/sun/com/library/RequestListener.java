package glide.sun.com.library;

import android.graphics.Bitmap;

public interface RequestListener {
    void onSuccess(Bitmap bitmap);

    void onFail();
}
