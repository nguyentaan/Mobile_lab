package com.example.bai4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsView extends View {
    Bitmap[] frames = new Bitmap[8];
    int i=0;

    long last_tick=0;
    long period=200;
    static Context ctext;

    static MediaPlayer mPlayer;
    public GraphicsView(Context context){

        super(context);
        ctext=context;
        frames[0]= BitmapFactory.decodeResource(getResources(), R.drawable.motion1);
        frames[1]= BitmapFactory.decodeResource(getResources(), R.drawable.motion2);
        frames[2]= BitmapFactory.decodeResource(getResources(), R.drawable.motion3);
        frames[3]= BitmapFactory.decodeResource(getResources(), R.drawable.motion4);
        frames[4]= BitmapFactory.decodeResource(getResources(), R.drawable.motion5);
        frames[5]= BitmapFactory.decodeResource(getResources(), R.drawable.motion6);
        frames[6]= BitmapFactory.decodeResource(getResources(), R.drawable.motion7);
        frames[7]= BitmapFactory.decodeResource(getResources(), R.drawable.motion8);
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(i<8){
            long time = (System.currentTimeMillis() - last_tick);
            if(time >= period){
                last_tick = System.currentTimeMillis();
                canvas.drawBitmap(frames[i],40,40,new Paint());
                i++;
                postInvalidate();
            } else {
                canvas.drawBitmap(frames[i],40,40, new Paint());
                postInvalidate();
            }
        } else {
            i=0;
            postInvalidate();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        i++;
        return true;
    }

}
