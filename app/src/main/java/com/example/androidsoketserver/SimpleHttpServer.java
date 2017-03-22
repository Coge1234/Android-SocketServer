package com.example.androidsoketserver;

/**
 * Created by Administrator on 2017/3/22.
 */

public class SimpleHttpServer {
    private boolean isEnable;

    /**
     * 启动Server（异步）
     */
    public void startAsync(){
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProSync();
            }
        }).start();
    }

    /**
     * 停止Server（异步）
     */
    public void stopServer(){

    }

    private void doProSync() {

    }
}
