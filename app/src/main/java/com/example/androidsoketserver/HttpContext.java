package com.example.androidsoketserver;

import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/22.
 */

public class HttpContext {

    private final HashMap<String, String> requestHeaders;

    public HttpContext() {
        requestHeaders = new HashMap<String, String>();
    }

    private Socket underlySocket;
    public void setUnderlySocket(Socket socket) {
        this.underlySocket = socket;
    }

    public Socket getUnderlySocket() {
        return underlySocket;
    }

    public void addRequestHeader(String headerName, String headerValue) {
        requestHeaders.put(headerName, headerValue);
    }
    public String getRequestHeaderValue(String headerName){
        return requestHeaders.get(headerName);
    }
}
