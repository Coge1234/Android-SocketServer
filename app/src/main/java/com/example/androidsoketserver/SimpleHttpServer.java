package com.example.androidsoketserver;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/22.
 */

public class SimpleHttpServer {
    private final WebConfiguration webConfig;
    private boolean isEnable;
    private ServerSocket socket;
    private final ExecutorService threadPool;
    private Set<IResourceUriHandler> resourcehandlers;

    public SimpleHttpServer(WebConfiguration webConfig) {
        this.webConfig = webConfig;
        threadPool = Executors.newCachedThreadPool();
        resourcehandlers = new HashSet<IResourceUriHandler>();
    }

    /**
     * 启动Server（异步）
     */
    public void startAsync() {
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
    public void stopServer() throws IOException {
        if (isEnable) {
            return;
        }
        isEnable = false;
        socket.close();
        socket = null;
    }

    private void doProSync() {

        try {
            InetSocketAddress socketAddress = new InetSocketAddress(webConfig.getPort());
            socket = new ServerSocket();
            socket.bind(socketAddress);
            while (isEnable) {
                final Socket remotePeer = socket.accept();
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("spy", "a remote peer acceped...." + remotePeer.getRemoteSocketAddress().toString());
                        onAcceptRemotePeer(remotePeer);
                    }
                });
            }
        } catch (IOException e) {
            Log.e("spy", e.toString());
        }
    }

    public void registerResourcehandler(IResourceUriHandler handler) {
        resourcehandlers.add(handler);
    }

    private void onAcceptRemotePeer(Socket remotePeer) {
        try {
            HttpContext httpContext = new HttpContext();
            httpContext.setUnderlySocket(remotePeer);
//            remotePeer.getOutputStream().write("congratulations, connected successful".getBytes());
            InputStream nis = remotePeer.getInputStream();
            String headerLine = null;
            String resourceUri = headerLine = StreamToolkit.readLine(nis).split(" ")[1];
            Log.d("spy", resourceUri);
            while ((headerLine = StreamToolkit.readLine(nis)) != null) {
                if (headerLine.equals("\r\n")) {
                    break;
                }
                String[] pair = headerLine.split(": ");
                if (pair.length > 1) {
                    httpContext.addRequestHeader(pair[0], pair[1].replace("\r\n",""));
                }
                Log.d("spy", headerLine);
            }
            for (IResourceUriHandler handler : resourcehandlers) {
                if (!handler.accept(resourceUri)) {
                    continue;
                }
                handler.handler(resourceUri, httpContext);
            }
        } catch (IOException e) {
            Log.e("spy", e.toString());
        } finally {
            try {
                remotePeer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
