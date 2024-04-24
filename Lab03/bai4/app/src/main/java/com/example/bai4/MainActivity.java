package com.example.bai4;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));

        GraphicsView.mPlayer= MediaPlayer.create(GraphicsView.ctext,R.raw.gangnam);
        GraphicsView.mPlayer.start();
    }
}