package com.example.lcq.audiojnitest.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lcq.audiojnitest.R;
import com.example.lcq.audiojnitest.tester.AudioPlayerTester;

public class MainActivity extends AppCompatActivity {

    private AudioPlayerTester mAudioPlayer;
    private boolean isPlaying = false;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();

        // Example of a call to a native method
        /*TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());*/
        Button start = (Button)findViewById(R.id.start);
        Button stop = (Button)findViewById(R.id.stop);
    }

    private void requestPermission() {
        final int permissionCheckRead = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        final int permissionCheckWrite = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // permission not granted yet -> don't check for registration state now
        if(permissionCheckRead != PackageManager.PERMISSION_GRANTED
                || permissionCheckWrite != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(  MainActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    public void startPlaying(View view){
        if (!isPlaying){
            mAudioPlayer = new AudioPlayerTester();
            if (mAudioPlayer != null){
                mAudioPlayer.startTesting();
            }
            isPlaying = true;
        }else{
            Toast.makeText(this,"请先停止当前的播放！",Toast.LENGTH_SHORT).show();
        }
    }

    public void stopPlaying(View view){
        if (mAudioPlayer != null){
            mAudioPlayer.stopTesting();
            isPlaying = false;
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
