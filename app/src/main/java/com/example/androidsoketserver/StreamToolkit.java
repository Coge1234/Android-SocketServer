package com.example.androidsoketserver;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/3/22.
 */

public class StreamToolkit {
    public static final String readLine(InputStream nis) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c1 = 0;
        int c2 = 0;
        while (c2 != -1 && !(c1 == '\r' && c2 == '\n')) {
            c1 = c2;
            c2 = nis.read();
            sb.append((char) c2);
        }
        if (sb.length() == 0){
            return null;
        }
        return sb.toString();
    }
}
