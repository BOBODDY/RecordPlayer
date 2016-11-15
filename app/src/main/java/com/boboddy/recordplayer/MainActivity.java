package com.boboddy.recordplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    
    RecordView record;
    Button songButton;
    
    private final static int MUSIC_INTENT = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        record = (RecordView) findViewById(R.id.record);
        
        RotateAnimation recordAnimation = new RotateAnimation(0, 180);
        recordAnimation.setRepeatCount(Animation.INFINITE);
        
        
        songButton = (Button) findViewById(R.id.button);
        songButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, MUSIC_INTENT);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        record.stopPlaying();
        setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MUSIC_INTENT && resultCode == RESULT_OK) {
            Uri uriSound = data.getData();
            record.setSong(uriSound);
            
            // TODO: parse this somehow and get song name, maybe read metadata?
            songButton.setText(uriSound.getPath());
        }
    }
}
