package com.msharbaji93.minddownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import com.msharbaji93.minddownloader.CacheManager.CacheManagerCallback;

/**
 * Created by MHDSHA on 07/07/2017.
 */

public class FileManager {

    private static final String TAG = "FileManager";

    // TODO: Should be removed once a job is finished
    public static final Map<View, String> runningJobs = Collections.synchronizedMap(new WeakHashMap<View, String>());

    private static CacheManager defaultCacheManager;

    private final Context context;

    public static CacheManager getDefaultCacheManager() {
        return defaultCacheManager;
    }

    public static void setDefaultCacheManager(CacheManager defaultCacheManager) {
        FileManager.defaultCacheManager = defaultCacheManager;
    }

    private ViewCallback viewCallback;

    private FileCallback fileCallback;

    public FileManager(final Context context) {
        defaultCacheManager = defaultCacheManager == null
                ? (defaultCacheManager = new CacheManager(
                Utils.createDefaultMemoryLruCache(context))) : defaultCacheManager;
        this.context = context;
    }


    public Context getContext() {
        return context;
    }

    public static void cleanUp() {
        defaultCacheManager.clear();
        runningJobs.clear();
    }

    public ViewCallback getViewCallback() {
        return viewCallback;
    }

    public void setViewCallback(ViewCallback viewCallback) {
        this.viewCallback = viewCallback;
    }

    public FileCallback getFileCallback() {
        return fileCallback;
    }

    public void setFileCallback(FileCallback fileCallback) {
        this.fileCallback = fileCallback;
    }

    public static Map<View, String> getRunningJobs() {
        return runningJobs;
    }

    public interface ViewCallback {
        void onImageLoaded(ImageView imageView, Bitmap bitmap);
    }

    public interface FileCallback {
        void onFileLoaded(Bitmap bitmap, final LoadedFrom source);
    }
}
