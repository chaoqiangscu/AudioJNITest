package com.example.lcq.audiojnitest.tester;

import android.os.Environment;

import com.example.lcq.audiojnitest.api.audio.AudioPlayer;
import com.example.lcq.audiojnitest.api.jniutils.JniUtils;
import com.example.lcq.audiojnitest.api.wav.WavFileReader;

import java.io.IOException;

public class AudioPlayerTester extends Tester {

    private static final String DEFAULT_TEST_FILE = Environment.getExternalStorageDirectory() + "/test.wav";

    private static final int SAMPLES_PER_FRAME = 1024;

    private AudioPlayer mAudioPlayer;
    private WavFileReader mWavFileReader;
    private volatile boolean mIsTestingExit = false;

    @Override
    public boolean startTesting() {
        mWavFileReader = new WavFileReader();
        mAudioPlayer = new AudioPlayer();

        try {
            mWavFileReader.openFile(DEFAULT_TEST_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        mAudioPlayer.startPlayer();

        new Thread(AudioPlayRunnable).start();

        return true;
    }

    @Override
    public boolean stopTesting() {
        mIsTestingExit = true;
        return true;
    }

    private Runnable AudioPlayRunnable = new Runnable() {
        @Override
        public void run() {
            byte[] buffer = new byte[SAMPLES_PER_FRAME * 8];        //注意：在此处可修改每次读入的字节数
            byte[] result;
            while (!mIsTestingExit && mWavFileReader.readData(buffer, 0, buffer.length) > 0) {
                result = JniUtils.wavFile(buffer);    //使用C++代码对数据进行处理
                mAudioPlayer.play(result, 0, result.length);
            }
            mAudioPlayer.stopPlayer();
            try {
                mWavFileReader.closeFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}
