//
// Created by lcq on 2018/5/16.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jbyteArray

JNICALL
Java_com_example_lcq_audiojnitest_api_jniutils_JniUtils_wavFile(
        JNIEnv *env, jobject /* this */,
        jbyteArray buf) {
    jbyte *cbuf;
    cbuf = env->GetByteArrayElements(buf, JNI_FALSE);
    jint sizeBuf = env->GetArrayLength(buf);
    if (NULL == cbuf) {
        return 0;
    }
    jbyteArray  result = env->NewByteArray(sizeBuf);
    env->SetByteArrayRegion(result,0,sizeBuf,cbuf);
    env->ReleaseByteArrayElements(buf,cbuf,0);
    return result;
}

