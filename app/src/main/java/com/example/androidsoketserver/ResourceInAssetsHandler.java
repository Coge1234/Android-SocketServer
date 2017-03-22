package com.example.androidsoketserver;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/22.
 */

public class ResourceInAssetsHandler implements IResourceUriHandler {
    public String acceptPrefix = "/static/";
    private Context mContext;

    public ResourceInAssetsHandler(Context context) {
        mContext = context;
    }

    @Override
    public boolean accept(String uri) {
        return uri.startsWith(acceptPrefix);
    }

    @Override
    public void handler(String uri, HttpContext httpContext) throws IOException {
//        try {
//            OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
//            PrintWriter writer = new PrintWriter(nos);
//            writer.println("HTTP/1.1 200 OK");
//            writer.println();
//            writer.println("from resource in  assets handler");
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        int startIndex = acceptPrefix.length();
        String assetsPath = uri.substring(startIndex);
        InputStream fis = mContext.getAssets().open(assetsPath);
        byte[]raw = StreamToolkit.readRawFromStream(fis);
        fis.close();
        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
        PrintStream printer = new PrintStream(nos);
        printer.println("HTTP/1.1 200 OK");
        printer.println("Content.Length:"+raw.length);
        if (assetsPath.endsWith(".html")){
            printer.println("Content-Type:text/html");
        }else if(assetsPath.endsWith(".js")){
            printer.println("Content-Type:text/js");
        }else if(assetsPath.endsWith(".css")){
            printer.println("Content-Type:text/css");
        }else if(assetsPath.endsWith(".jpg")){
            printer.println("Content-Type:text/jpg");
        }
        printer.println();
        printer.write(raw);
    }
}
