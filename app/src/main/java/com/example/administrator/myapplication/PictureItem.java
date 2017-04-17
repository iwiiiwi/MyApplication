package com.example.administrator.myapplication;

import android.graphics.Bitmap;
import android.graphics.Path;

/**
 * Created by Administrator on 2017/4/17.
 */

public class PictureItem {

    private boolean isEraserMode;
    private Path path;
    private Bitmap bitmap;

    public PictureItem(int width,int height) {
        this(false,new Path(),Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
    }

    public PictureItem(boolean isEraserMode, Path path, Bitmap bitmap) {
        this.isEraserMode = isEraserMode;
        this.path = path;
        this.bitmap = bitmap;
    }

    public boolean isEraserMode() {
        return isEraserMode;
    }

    public void setEraserMode(boolean eraserMode) {
        isEraserMode = eraserMode;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
