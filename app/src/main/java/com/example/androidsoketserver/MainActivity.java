package com.example.androidsoketserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        shs.startAsync();
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
