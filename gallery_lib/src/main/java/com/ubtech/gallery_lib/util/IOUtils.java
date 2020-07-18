package com.ubtech.gallery_lib.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    public static void close(OutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void flush(OutputStream stream) {
        if (stream != null) {
            try {
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
