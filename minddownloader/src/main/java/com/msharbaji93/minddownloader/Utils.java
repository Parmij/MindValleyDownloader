package com.msharbaji93.minddownloader;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * Created by MHDSHA on 05/07/2017.
 */

public class Utils {

    private static final String TAG = "Utils";
    static final char KEY_SEPARATOR = '\n';

    @SuppressWarnings("unchecked")
    static <T> T getService(Context context, String service) {
        return (T) context.getSystemService(service);
    }


    static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = getService(context, ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        int memoryClass = largeHeap ? am.getLargeMemoryClass() : am.getMemoryClass();
        // Target 20% of the available heap.
        return (int) (1024L * 1024L * memoryClass / 5);
    }

    static int getBitmapBytes(Bitmap bitmap) {
        int result = SDK_INT >= KITKAT ? bitmap.getAllocationByteCount() : bitmap.getByteCount();
        if (result < 0) {
            throw new IllegalStateException("Negative size: " + bitmap);
        }
        return result;
    }

    public static MemoryLruCache createDefaultMemoryLruCache(Context context) {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int cacheSize = calculateMemoryCacheSize(context); // TODO: We should use

        // Target 20% of the available heap.
        Log.e(TAG, "Initializing LruCache with size " + cacheSize + "kb");

        return new MemoryLruCache(cacheSize);
    }

    public static int dpToPx(final Context context, final int dp) {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int)((dp * scale) + 0.5f);
    }




}
