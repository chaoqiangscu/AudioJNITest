package com.example.lcq.audiojnitest.api.jniutils;

public class JniUtils {
    public static native String stringFromJNI();
    public static native byte[] wavFile(byte[] buf);
    public static native void wavByte2Double(byte[] origByte, double[] data, int frameLen);
    public static native void wavDouble2Byte(double[] data, byte[]  dataByte, int frameLen);
}
