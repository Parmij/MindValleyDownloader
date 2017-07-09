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

//    public void load(final String urlString, final ImageView imageView, final JobOptions options) {
//        if (urlString == null || urlString == "")
//            return;
//
//        final FileManager self = this;
//
//        load(urlString, imageView, options, new CacheManagerCallback() {
//            @Override
//            public void onFileLoaded(final Object bitmap, final LoadedFrom source) {
//                if (bitmap == null) {
//                    work(urlString, imageView, options);
//                } else {
//                    final FileProcessorCallback callback = new FileProcessorCallback(self, urlString, imageView, options);
//                    callback.onFileLoaded((Bitmap) bitmap, LoadedFrom.NETWORK);
//                }
//            }
//        });
//    }

//    public void load(final String urlString) {
//        if (urlString == null || urlString == "")
//            return;
//
//        cacheManager.get(urlString, new CacheManagerCallback() {
//            @Override
//            public void onFileLoaded(final Object bitmap, final LoadedFrom source) {
//                if (bitmap != null) {
//                    if (fileCallback != null) {
//                        uiHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                fileCallback.onFileLoaded((Bitmap) bitmap);
//                            }
//                        });
//                    }
//                } else {
//                    work(urlString);
//                }
//            }
//        });
//    }

//    private void load(final String urlString, final View view, final JobOptions options, final CacheManagerCallback callback) {
//        runningJobs.put(view, urlString);
//
//        if(view instanceof ImageView) {
//            if (JobOptions.getPlaceholderResId() != NO_PLACEHOLDER)
//                CustomDrawable.setPlaceholder((ImageView) view, placeholderResId, null);
//            cacheManager.get(getCacheKeyForJob(urlString, options), callback);
//        }
//    }

//    private void work(final String url) {
//        byte[] binaryData = null;
//        try {
//            binaryData = OkHttp3Downloader.getInstance().load(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (binaryData == null) {
//            Log.e(TAG, "queueJob got null binaryData");
//            return;
//        }
//
//        final Bitmap bitmap = FileProcessor.decodeByteArray(binaryData, null);
//
//        if (bitmap == null) {
//            Log.e(TAG, "queueJob got NULL bitmap");
//            return;
//        }
//
//        cacheManager.put(url, bitmap);
//
//        if (fileCallback != null) {
//            uiHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    fileCallback.onFileLoaded(bitmap);
//                }
//            });
//        }
//
//    }




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
