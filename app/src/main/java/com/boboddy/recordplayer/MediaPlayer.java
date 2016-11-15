package com.boboddy.recordplayer;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by boboddy on 6/3/2016.
 */
public class MediaPlayer implements android.media.MediaPlayer.OnPreparedListener{
    
    Uri currentSong;
    
    Context ctx;
    
    Uri crackle;
    
    android.media.MediaPlayer songPlayer, cracklePlayer;

    public MediaPlayer(Context ctx) {
        songPlayer = new android.media.MediaPlayer();
        songPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        
        cracklePlayer = new android.media.MediaPlayer();
        cracklePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        cracklePlayer.setLooping(true);
        // TODO: look up user preference for crackle, load appropriate file
        crackle = Uri.parse("android.resource://com.boboddy.recordplayer/" + R.raw.crackle);
        
        this.ctx = ctx;
        
        songPlayer.setOnPreparedListener(this);
    }

    @Override
    public void onPrepared(android.media.MediaPlayer mp) {
        Log.d("Record", "done preparing, time to start");
//        mp.start();
    }

    public void setCurrentSong(Uri song) {
        currentSong = song;
        
        songPlayer.reset();
        cracklePlayer.reset();
        
        try {
            songPlayer.setDataSource(ctx, song);
            cracklePlayer.setDataSource(ctx, crackle);
            
//            songPlayer.prepareAsync();
            songPlayer.prepare();
            cracklePlayer.prepare();
        }catch (IOException ioe) {
            Log.e("Record", "Error playing song", ioe);
        }
    }
    
    public void toggle() {
        if(songPlayer.isPlaying()) {
            pause();
        } else {
            play();
        }
    }
    
    public void play() {
        cracklePlayer.start();
        songPlayer.start();
    }
    
    public void pause() {
        cracklePlayer.pause();
        songPlayer.pause();
    }
    
    public void stopPlaying() {
        cracklePlayer.stop();
        songPlayer.stop();

        cracklePlayer.reset();
        songPlayer.reset();
    }
}
