package glide.sun.com.glide_mode;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import glide.sun.com.library.Glide;
import glide.sun.com.library.RequestListener;


public class MainActivity extends AppCompatActivity {
    private LinearLayout lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lin = findViewById(R.id.lin);
        findViewById(R.id.single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSingle();
            }
        });
    }


    private void loadSingle() {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        lin.addView(imageView);
        Glide.with(this).load("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2622872143,403205946&fm=58&bpow=600&bpoh=860").loading(R.mipmap.ic_launcher).setListener(new RequestListener() {

            @Override
            public void onSuccess(Bitmap bitmap) {
                Log.e("TAg","onSuccess");
            }

            @Override
            public void onFail() {
                Log.e("TAg","onFail");
            }
        }).init(imageView);
    }
}
