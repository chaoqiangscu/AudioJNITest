package com.example.lcq.audiojnitest.api.jniutils;

public class JniUtils {
    public static native String stringFromJNI();
    public static native byte[] wavFile(byte[] buf);
}
