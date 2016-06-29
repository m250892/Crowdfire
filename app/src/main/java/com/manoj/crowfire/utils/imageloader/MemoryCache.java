package com.manoj.crowfire.utils.imageloader;

import android.graphics.Bitmap;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by manoj on 29/06/16.
 */
public class MemoryCache {

    private static final int CACHE_SIZE = 10;
    private Map<String, Bitmap> cache = Collections.synchronizedMap(
            new LinkedHashMap<String, Bitmap>(CACHE_SIZE, 1.5f, true));
    private long size = 0;
    private long limit = 0;


    private MemoryCache() {
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    private static MemoryCache instance;

    public synchronized static MemoryCache getInstance() {
        if (instance == null) {
            instance = new MemoryCache();
        }
        return instance;
    }

    public void setLimit(long new_limit) {
        limit = new_limit;
    }

    public Bitmap get(String id) {
        if (cache == null || !cache.containsKey(id))
            return null;
        return cache.get(id);
    }

    public void put(String id, Bitmap bitmap) {
        if (cache == null) {
            return;
        }
        try {
            if (cache.containsKey(id))
                size -= getSizeInBytes(cache.get(id));
            cache.put(id, bitmap);
            size += getSizeInBytes(bitmap);
            checkSize();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void checkSize() {
        if (size > limit) {
            Iterator<Map.Entry<String, Bitmap>> iter = cache.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Bitmap> entry = iter.next();
                size -= getSizeInBytes(entry.getValue());
                iter.remove();
                if (size <= limit)
                    break;
            }
        }
    }

    public void clear() {
        if (cache != null) {
            cache.clear();
            size = 0;
        }
    }

    long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
