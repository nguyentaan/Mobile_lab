package com.example.bai1;

import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.graphics.Color;
import android.view.View;
import android.graphics.Canvas;
import android.content.Context;

public class GraphicsView extends View {
    public GraphicsView (Context context){
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas){
            Rect r = new Rect(40, 40, 400, 200);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawRect(r, paint);
        invalidate();
    }

}
