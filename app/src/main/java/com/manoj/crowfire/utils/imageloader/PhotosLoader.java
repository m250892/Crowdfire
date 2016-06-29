package com.manoj.crowfire.utils.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.manoj.crowfire.utils.NetworkUtils;

import java.io.IOException;
import java.io.InputStream;

class PhotosLoader implements Runnable {
    PhotoToLoad photoToLoad;

    PhotosLoader(PhotoToLoad photoToLoad) {
        this.photoToLoad = photoToLoad;
    }

    @Override
    public void run() {
        if (ImageLoader.getInstance().imageViewReused(photoToLoad)) {
            return;
        }
        Bitmap bmp = getBitmap(photoToLoad.url);
        if (ImageLoader.getInstance().imageViewReused(photoToLoad)) {
            return;
        }
        //adding to cache
        if (bmp != null) {
            addBitmapToMemoryCache(photoToLoad.url, bmp);
        }

        BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
        photoToLoad.imageView.post(bd);
    }

    private void addBitmapToMemoryCache(String url, Bitmap bmp) {
        MemoryCache.getInstance().put(url, bmp);
    }

    private Bitmap getBitmap(String url) {
        try {
            InputStream inputStream = NetworkUtils.httpGetCall(url);
            if (inputStream == null) {
                return null;
            }
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            return getResizeBitmap(image);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public Bitmap getResizeBitmap(Bitmap bm) {
        if (bm == null) return bm;
        int width = bm.getWidth();
        int height = bm.getHeight();

        int sampleSize = calculateInSampleSize(width, height, 500, 500);
        int newWidth = width / sampleSize;
        int newHeight = height / sampleSize;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public int calculateInSampleSize(
            int width, int height, int reqWidth, int reqHeight) {

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    || (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}