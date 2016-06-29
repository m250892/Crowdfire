package com.manoj.crowfire.utils.imageloader;

import android.graphics.Bitmap;

import com.manoj.crowfire.R;

class BitmapDisplayer implements Runnable {
    Bitmap bitmap;
    PhotoToLoad photoToLoad;

    public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
        bitmap = b;
        photoToLoad = p;
    }

    public void run() {
        if (ImageLoader.getInstance().imageViewReused(photoToLoad))
            return;
        if (bitmap != null) {
            photoToLoad.imageView.setImageBitmap(bitmap);
        } else {
            photoToLoad.imageView.setImageResource(R.drawable.no_image_available);
        }
    }
}