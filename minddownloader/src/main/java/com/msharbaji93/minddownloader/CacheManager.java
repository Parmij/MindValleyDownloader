package com.msharbaji93.minddownloader;

import android.util.Log;

/**
 * Created by MHDSHA on 07/07/2017.
 */

public class CacheManager implements MemoryLruCache.MemoryCacheEntryRemovedCallback {

    private static final String TAG = "CacheManager";
    private final MemoryLruCache memoryLruCache;

    public CacheManager(MemoryLruCache memoryLruCache) {
        this.memoryLruCache = memoryLruCache;
        memoryLruCache.setEntryRemovedCallback(this);
    }

    public MemoryLruCache getMemoryLruCache() {
        return memoryLruCache;
    }

    @Override
    public void onEntryRemoved(boolean evicted, String key, Object oldValue, Object newValue) {
        if (oldValue == null)
            return;

        // Add the just evicted memory cache entry to disk cache (2nd level cache)
    }

    public void get(final String id, final CacheManagerCallback callback) {
        final Object bitmap = getFileFromLRUCache(id);

        if (callback != null) {
            callback.onFileLoaded(bitmap, LoadedFrom.MEMORY);
            return;
        }
    }

    public void get(final String id, final CacheManagerStringCallback callback) {
        final Object object = getFileFromLRUCache(id);

        if (callback != null) {
            callback.onStringLoaded(object, LoadedFrom.MEMORY);
            return;
        }
    }

    public void put(final String key, final Object object) {
        if (getFileFromLRUCache(key) != null)
            return;
        memoryLruCache.put(key, object);
    }

    private Object getFileFromLRUCache(final String urlString) {
        final Object cachedBitmap = memoryLruCache.get(urlString);

        if (cachedBitmap == null)
            return null;

        Log.v(TAG, "Item loaded from LRU cache: " + urlString);

        return cachedBitmap;
    }


    public void clear() {
        memoryLruCache.evictAll();
    }

    public static interface CacheManagerCallback {
        void onFileLoaded(final Object object, final LoadedFrom source);
    }

    public static interface CacheManagerStringCallback {
        void onStringLoaded(final Object object, final LoadedFrom source);
    }

}
