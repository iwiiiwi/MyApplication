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

public class DrawView extends ImageView {
    private Paint mpaint;
    private Canvas mcanvas;
    private Bitmap mbitmap,mpen;
    private float startX;
    private float startY;
    private Path mpath;
    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mpaint=new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.GREEN);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                    mpen=BitmapFactory.decodeResource(getResources(),R.mipmap.pen);
                    mcanvas = new Canvas(mbitmap);
                    mpath=new Path();
                    mcanvas.drawColor(Color.WHITE);
                }
                startX=event.getX();
                startY=event.getY();
                mcanvas.drawColor(Color.WHITE);
                mcanvas.drawPath(mpath,mpaint);
                break;
            case MotionEvent.ACTION_MOVE:
                float stopX = event.getX();
                float stopY = event.getY();
                mcanvas.drawColor(Color.WHITE);
                float x=mpen.getWidth()+1;
                float y=mpen.getHeight()+1;
                mpath.moveTo(startX-x,startY+y);
                mpath.lineTo(stopX-x,stopY+y);
                mcanvas.drawPath(mpath,mpaint);
                mcanvas.drawBitmap(mpen,startX-mpen.getWidth(),startY,mpaint);
                startX = event.getX();
                startY = event.getY();
                setImageBitmap(mbitmap);
                break;
            case MotionEvent.ACTION_UP:
                mcanvas.drawColor(Color.WHITE);
                mcanvas.drawPath(mpath,mpaint);
                break;
            default:
                break;
        }
        return true;
    }
}
