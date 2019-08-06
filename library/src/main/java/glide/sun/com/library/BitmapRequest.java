package glide.sun.com.library;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

public class BitmapRequest {
    //图片请求地址
    private String url;

    //上下文
    private Context context;

    //占位图
    private int resId;

    //展示图片控件
    private SoftReference<ImageView> imageView;

    //图片监听
    private RequestListener requestListener;

    //MD5地址 为了防止图片错位
    private String md5Url;


    public BitmapRequest(Context context){
        this.context=context.getApplicationContext();
    }
    //加载图片
    public BitmapRequest load(String url){
        this.url=url;
        this.md5Url=MD5Util.md5(url);
        return this;
    }
    //设置占位图
    public BitmapRequest loading(int resId){
        this.resId=resId;
        return this;
    }
    //设置监听
    public BitmapRequest setListener(RequestListener  requestListener){
        this.requestListener=requestListener;
        return this;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    //将图片显示到imageView上
    public void init(ImageView imageView){
        imageView.setTag(md5Url);
        this.imageView=new SoftReference<ImageView>(imageView);
        //请求加载图片
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public String getMd5Url() {
        return md5Url;
    }

    public int getResId() {
        return resId;
    }

    public Context getContext() {
        return context;
    }
}
