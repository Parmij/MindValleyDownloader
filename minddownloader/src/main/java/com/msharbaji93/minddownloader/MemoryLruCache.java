package com.msharbaji93.minddownloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by MHDSHA on 07/07/2017.
 */

public class MemoryLruCache extends LruCache<String, Object> {

    private MemoryCacheEntryRemovedCallback onEntryRemovedCallback;


    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public MemoryLruCache(int maxSize) {
        super(maxSize);
    }

    public void setEntryRemovedCallback(final MemoryCacheEntryRemovedCallback onEntryRemovedCallback) {
        this.onEntryRemovedCallback = onEntryRemovedCallback;
    }

    @Override
    protected int sizeOf(final String key, final Object object) {
        // The cache size will be measured in kilobytes rather than number of items.
        if (object instanceof Bitmap)
            return Utils.getBitmapBytes((Bitmap) object) / 1024;
        else if (object instanceof String)
            return ((String) object).length();
        return -1;
    }

    @Override
    protected void entryRemoved(final boolean evicted, final String key, final Object oldValue, final Object newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);

        if (onEntryRemovedCallback != null) {
            onEntryRemovedCallback.onEntryRemoved(evicted, key, oldValue, newValue);
        }
    }

    public static interface MemoryCacheEntryRemovedCallback {
        void onEntryRemoved(boolean evicted, String key, Object oldValue, Object newValue);
    }

}
