package com.example.androidsoketserver;

import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/22.
 */

public class UploadImageHandler implements IResourceUriHandler {
    public String acceptPrefix = "/upload_image/";

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
//            writer.println("from upload image handler");
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String tmPath = Environment.getExternalStorageDirectory().getPath() + "/test_upload.jpg";
        long totallength = Long.parseLong(httpContext.getRequestHeaderValue("Content-Length"));
        FileOutputStream fos = new FileOutputStream(tmPath);
        InputStream nis = httpContext.getUnderlySocket().getInputStream();
        byte[] buffer = new byte[10240];
        int nRead = 0;
        long nLeftLength = totallength;
        while (nLeftLength > 0 && (nRead = nis.read(buffer))>0) {
            fos.write(buffer,0,nRead);
        }
        fos.close();
        OutputStream nos = httpContext.getUnderlySocket().getOutputStream();
        PrintStream printer = new PrintStream(nos);
        printer.println("HTTP/1.1 200 OK");
        printer.println();

        onImageLoaded(tmPath);
    }
    protected void onImageLoaded(String path){

    }
}
