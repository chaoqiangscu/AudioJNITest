//
// Created by lcq on 2018/5/21.
//

#include "byte2double.h"
#include <jni.h>
#include <iostream>
#include <fstream>
#include <string.h>
#include<math.h>
#include<cmath>
#include<stdlib.h>
#include <bitset>
#include <iomanip>

using namespace std;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_lcq_audiojnitest_api_jniutils_JniUtils_wavByte2Double(JNIEnv *env, jobject ob,
                                                                       jbyteArray origByte_,
                                                                       jdoubleArray data_,
                                                                       jint frameLen) {
jbyte *origByte = env->GetByteArrayElements(origByte_, NULL);
jdouble *data = env->GetDoubleArrayElements(data_, NULL);

// TODO
    for (int ii = 0, jj = 0; ii < frameLen; ii += 2, jj++)
    {
        unsigned char low = origByte[ii];
        unsigned short high = origByte[ii + 1];

        short tmpData = high * 256 + low;
        short data_complement = 0;

        if ((short)(high / 128) == 1)
        {
            data_complement = tmpData - 65536;
        }
        else
        {
            data_complement = tmpData;
        }

        data[jj] = (double)(data_complement / (double)32768);

    }

env->ReleaseByteArrayElements(origByte_, origByte, 0);
env->ReleaseDoubleArrayElements(data_, data, 0);
}

/*void wavByte2Double(char * origByte, double * data, int frameLen)
{

    for (int ii = 0, jj = 0; ii < frameLen; ii += 2, jj++)
    {
        unsigned char low = origByte[ii];
        unsigned short high = origByte[ii + 1];

        short tmpData = high * 256 + low;
        short data_complement = 0;

        if ((short)(high / 128) == 1)
        {
            data_complement = tmpData - 65536;
        }
        else
        {
            data_complement = tmpData;
        }

        data[jj] = (double)(data_complement / (double)32768);

    }
}*/

extern "C"
JNIEXPORT void JNICALL
Java_com_example_lcq_audiojnitest_api_jniutils_JniUtils_wavDouble2Byte(JNIEnv *env, jobject type,
                                                                       jdoubleArray data_,
                                                                       jbyteArray dataByte_,
                                                                       jint frameLen) {
    jdouble *data = env->GetDoubleArrayElements(data_, NULL);
    jbyte *dataByte = env->GetByteArrayElements(dataByte_, NULL);

    // TODO
    for (int ii = 0, jj = 0; ii < frameLen; ii += 2, jj++)
    {
        short dataShort = (short)(data[jj] * 32768);
        dataByte[ii + 1] = dataShort / 256;
        dataByte[ii] = dataShort - dataByte[ii + 1] * 256;

    }

    env->ReleaseDoubleArrayElements(data_, data, 0);
    env->ReleaseByteArrayElements(dataByte_, dataByte, 0);
}

/*
void wavDouble2Byte(double* data, unsigned char * dataByte, int frameLen)
{
    for (int ii = 0, jj = 0; ii < frameLen; ii += 2, jj++)
    {
        short dataShort = (short)(data[jj] * 32768);
        dataByte[ii + 1] = dataShort / 256;
        dataByte[ii] = dataShort - dataByte[ii + 1] * 256;

    }
}*/
