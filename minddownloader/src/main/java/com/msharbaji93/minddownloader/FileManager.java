package com.msharbaji93.minddownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import com.msharbaji93.minddownloader.CacheManager.CacheManagerCallback;

/**
 * Created by MHDSHA on 07/07/2017.
 */

public class FileManager {

    private static final String TAG = "FileManager";

    public static final int NO_PLACEHOLDER = -1;

    // TODO: Should be removed once a job is finished
    private static final Map<View, String> runningJobs = Collections.synchronizedMap(new WeakHashMap<View, String>());

    private static CacheManager defaultCacheManager;

    private static final Handler uiHandler = new Handler(Looper.getMainLooper());

    private final Context context;

    private final CacheManager cacheManager;

    private ViewCallback viewCallback;

    private FileCallback fileCallback;

    private int placeholderResId = Color.parseColor("#eeeeee");

    public FileManager(final Context context) {
        this(context,
                defaultCacheManager == null
                        ? (defaultCacheManager = new CacheManager(
                        Utils.createDefaultMemoryLruCache(context))) : defaultCacheManager);
    }

    public FileManager(final Context context, final CacheManager cacheManager) {

        this.context = context;
        this.cacheManager = cacheManager;
    }

    public Context getContext() {
        return context;
    }

    public static void cleanUp() {
        defaultCacheManager.clear();
        runningJobs.clear();
    }

    public void load(final String urlString, final ImageView imageView, final JobOptions options) {
        if (urlString == null || urlString == "")
            return;

        final FileManager self = this;

        load(urlString, imageView, options, new CacheManagerCallback() {
            @Override
            public void onFileLoaded(final Object bitmap,final LoadedFrom source) {
                if (bitmap == null) {
                    work(urlString, imageView, options);
                } else {
                    final FileProcessorCallback callback = new FileProcessorCallback(self, urlString, imageView, options);
                    callback.onFileLoaded((Bitmap) bitmap, source);
                }
            }
        });
    }

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

    private void load(final String urlString, final View view, final JobOptions options, final CacheManagerCallback callback) {
        runningJobs.put(view, urlString);

        if(view instanceof ImageView) {
            if (placeholderResId != NO_PLACEHOLDER)
                CustomDrawable.setPlaceholder((ImageView) view, placeholderResId, null);
            cacheManager.get(getCacheKeyForJob(urlString, options), callback);
        }
    }

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

    private void work(final String url, final ImageView imageView, final JobOptions options) {
        final FileProcessorCallback callback = new FileProcessorCallback(this, url, imageView, options);

        FileProcessor.decodeSampledBitmapFromRemoteUrl(context, url, options.requestedWidth, options.requestedHeight, callback);
    }

    public static String getCacheKeyForJob(final String url, final JobOptions options) {
//        if (options.requestedHeight <= 0 && options.requestedWidth <= 0)
            return url;
//        return String.format("%s-%sx%s", url, options.requestedWidth, options.requestedHeight);
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setPlaceholderResId(final int placeholderResId) {
        this.placeholderResId = placeholderResId;
    }

    public int getPlaceholderResId() {
        return placeholderResId;
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
        void onFileLoaded(Bitmap bitmap);
    }
}
