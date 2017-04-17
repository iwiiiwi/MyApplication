package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/4/12.
 */

public class DrawViewWithoutPen extends FrameLayout {
    private ImageView canvasView,penAndEraserFlag;
    private final static float PAINT_WIDTH_BOLD=10;
    private final static float PAINT_WIDTH_MID=5;
    private final static float PAINT_WIDTH_HAIR=0;
    private Paint mpaint;
    private Canvas mcanvas;
    private Bitmap mbitmap;
    private float startX;
    private float startY;
    private Path mpath;
    private boolean isEraserMode;

    public DrawViewWithoutPen(Context context) {
        super(context);
    }

    public DrawViewWithoutPen(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DrawViewWithoutPen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mbitmap == null) {
                    mbitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                    mcanvas = new Canvas(mbitmap);
                    mcanvas.drawColor(Color.WHITE);
                    mpath = new Path();
                }
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float stopX = event.getX();
                float stopY = event.getY();
                mcanvas.drawLine(startX, startY, stopX, stopY, mpaint);
                startX = event.getX();
                startY = event.getY();
                canvasView.setImageBitmap(mbitmap);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    private void switchToEraser() {
        isEraserMode = true;
        penAndEraserFlag.setImageResource(R.mipmap.eraser);
        mpaint.setColor(Color.WHITE);
        mpaint.setStrokeWidth(PAINT_WIDTH_BOLD);
    }

    private void switchToPaint() {
        isEraserMode = false;
        penAndEraserFlag.setImageResource(R.mipmap.pen);
        mpaint.setColor(Color.GREEN);
        mpaint.setStrokeWidth(PAINT_WIDTH_MID);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_drawview,this);
        canvasView= (ImageView) findViewById(R.id.pictureContainer);
        penAndEraserFlag= (ImageView) findViewById(R.id.penAndEraserFlag);
        penAndEraserFlag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEraserMode){
                    switchToPaint();
                }else {
                    switchToEraser();
                }
            }
        });
        mpaint = new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.GREEN);
    }

}
