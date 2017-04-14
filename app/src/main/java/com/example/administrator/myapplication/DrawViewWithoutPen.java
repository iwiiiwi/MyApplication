package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/4/12.
 */

public class DrawViewWithoutPen extends ImageView {
    private Paint mpaint;
    private Canvas mcanvas;
    private Bitmap mbitmap;
    private float startX;
    private float startY;
    public DrawViewWithoutPen(Context context) {
        super(context);
    }

    public DrawViewWithoutPen(Context context, AttributeSet attrs) {
        super(context, attrs);
        mpaint=new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.GREEN);
    }

    public DrawViewWithoutPen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mpaint=new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.GREEN);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(mbitmap==null) {
                    mbitmap=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
                    mcanvas = new Canvas(mbitmap);
                    mcanvas.drawColor(Color.WHITE);
                }
                startX=event.getX();
                startY=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float stopX = event.getX();
                float stopY = event.getY();
                mcanvas.drawLine(startX,startY,stopX,stopY,mpaint);
                startX = event.getX();
                startY = event.getY();
                setImageBitmap(mbitmap);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
}
