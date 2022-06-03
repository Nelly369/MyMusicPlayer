package com.dam.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer = new MediaPlayer();


    private static final String TAG = "MainActivity";

    public void play(View view){
        mediaPlayer.start();
        Log.i(TAG, "play");

    }
    public void pause(View view){
        mediaPlayer.pause();
        Log.i(TAG, "pause");
    }

    //Start Volume
    private void volume(){
        //Association de le seekbar
        SeekBar sbVolume = findViewById(R.id.sbVolume);

        //Initialiser le manager en tant que service
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        //Volume max du terminal
        int volumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //Valorisation de cette valeur au max  de la seekbar
        sbVolume.setMax(volumeMax);

        int currentVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //Ajustement de la position du curseur
        sbVolume.setProgress(currentVolume);

        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "onProgressChanged: volume ="+ Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    //End Volume

    private void position(){
        SeekBar sbPosition = findViewById(R.id.sbPosition);
        //Définir la valeur max
        sbPosition.setMax(mediaPlayer.getDuration());

        //Part one la gestion du déplacement du curseur par l'utilisateur
        sbPosition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "Position dans le morceau :" + Integer.toString(progress));
                //mediaPlayer.seekTo(sbPosition.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pause(sbPosition);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                play(sbPosition);
                mediaPlayer.seekTo(sbPosition.getProgress());

            }
        });

        //Part two gestion du déplacement du curseur par l'application
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //déplacement automatique
                sbPosition.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 300);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Méthode 1**/
        //mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        //mediaPlayer.start();

        /**Méthode 2**/
        mediaPlayer = MediaPlayer.create(this, R.raw.sound3);
        mediaPlayer1 = MediaPlayer.create(this, R.raw.sound2);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.sound);
        volume();
        position();

    }

}