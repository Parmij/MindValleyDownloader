package com.msharbaji93.minddownloader;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * Created by MHDSHA on 05/07/2017.
 */

public class Utils {

    static final char KEY_SEPARATOR = '\n';

    @SuppressWarnings("unchecked")
    static <T> T getService(Context context, String service) {
        return (T) context.getSystemService(service);
    }


    static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = getService(context, ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        int memoryClass = largeHeap ? am.getLargeMemoryClass() : am.getMemoryClass();
        // Target ~15% of the available heap.
        return (int) (1024L * 1024L * memoryClass / 7);
    }

    static int getBitmapBytes(Bitmap bitmap) {
        int result = SDK_INT >= KITKAT ? bitmap.getAllocationByteCount() : bitmap.getByteCount();
        if (result < 0) {
            throw new IllegalStateException("Negative size: " + bitmap);
        }
        return result;
    }
}
