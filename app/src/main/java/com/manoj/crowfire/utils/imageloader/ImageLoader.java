package com.manoj.crowfire.utils.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.manoj.crowfire.R;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by manoj on 29/06/16.
 */
public class ImageLoader {
    private static final float LOW_MEMORY_THRESHOLD_PERCENTAGE = 5;
    private static final int THREAD_POOL_SIZE = 5;
    MemoryCache memoryCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private ExecutorService executorService;
    private int loadingDrawableId = R.drawable.image_while_loading;
    private int errorDrawableId = R.drawable.no_image_available;

    private static ImageLoader instance;

    private ImageLoader() {
        memoryCache = MemoryCache.getInstance();
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public synchronized static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public void loadImage(String url, ImageView imageView) {
        if (!isAdequateMemoryAvailable()) {
            return;
        }

        if (url == null) {
            //load default image
            imageView.setImageResource(errorDrawableId);
            return;
        }
        imageViews.put(imageView, url);
        Bitmap bitmap = getBitmapFromMemCache(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            imageView.setImageResource(loadingDrawableId);
            submitToPhotoDownloadQueue(url, imageView);
        }
    }

    private Bitmap getBitmapFromMemCache(String url) {
        return memoryCache.get(url);
    }

    private void submitToPhotoDownloadQueue(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        return (tag == null || !tag.equals(photoToLoad.url));
    }

    private static boolean isAdequateMemoryAvailable() {
        boolean isAdequateMemoryAvailable = true;
        // Get app memory info
        long total = Runtime.getRuntime().maxMemory();
        long used = Runtime.getRuntime().totalMemory();
        float percentAvailable = 100f * (1f - ((float) used / total));
        if (percentAvailable <= LOW_MEMORY_THRESHOLD_PERCENTAGE) {
            isAdequateMemoryAvailable = false;
            handleLowMemory();
        }

        return isAdequateMemoryAvailable;
    }

    private static void handleLowMemory() {
        // handle low memory
        System.gc();
    }

}
