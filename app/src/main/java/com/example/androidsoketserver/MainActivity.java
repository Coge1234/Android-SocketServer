package com.example.androidsoketserver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SimpleHttpServer shs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebConfiguration wc = new WebConfiguration();
        wc.setPort(8088);
        wc.setMaxParallels(50);
        shs = new SimpleHttpServer(wc);
        shs.registerResourcehandler(new ResourceInAssetsHandler(this));
        shs.registerResourcehandler(new UploadImageHandler(){
            @Override
            protected void onImageLoaded(String path) {
                showImage(path);
            }
        });
        shs.startAsync();
    }

    private void showImage(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                ivImage.setImageBitmap(bitmap);
                Toast.makeText(MainActivity.this, "显示图片成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {

        try {
            shs.stopServer();
        } catch (IOException e) {
            Log.e("spy",e.toString());
        }
        super.onDestroy();
    }
}
