package com.example.androidsoketserver;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/22.
 */

public interface IResourceUriHandler {
    boolean accept(String uri);

    void handler(String uri, HttpContext httpContext) throws IOException;
}
