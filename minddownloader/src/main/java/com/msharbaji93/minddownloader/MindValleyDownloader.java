package com.msharbaji93.minddownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by MHDSHA on 06/07/2017.
 */

public class MindValleyDownloader {
    private static final String TAG = "MindValleyDownloader";
    private static final int NO_PLACEHOLDER = -1;
    private Context context;
    private FileManager fileManager;

    public MindValleyDownloader(Context context) {
        this.context = context;
        this.fileManager = new FileManager(context);
    }

    public void load(final String urlString, final ImageView imageView, final JobOptions options) {
        if (urlString == null || urlString == "")
            return;
        load(urlString, imageView, options, new CacheManager.CacheManagerCallback() {
            @Override
            public void onFileLoaded(final Object bitmap, final LoadedFrom source) {
                if (bitmap == null) {
                    work(urlString, imageView, options);
                } else {
                    final FileProcessorCallback callback = new FileProcessorCallback(fileManager, urlString);
                    callback.setOptions(options);
                    callback.setView(imageView);
                    callback.onFileLoaded((Bitmap) bitmap, LoadedFrom.NETWORK);
                }
            }
        });
    }

    public void load(final String urlString, final ImageView imageView) {
        if (urlString == null || urlString == "")
            return;
        final JobOptions options = new JobOptions();
        load(urlString, imageView, options, new CacheManager.CacheManagerCallback() {
            @Override
            public void onFileLoaded(final Object bitmap, final LoadedFrom source) {
                if (bitmap == null) {
                    work(urlString, imageView, options);
                } else {
                    final FileProcessorCallback callback = new FileProcessorCallback(fileManager, urlString);
                    callback.setOptions(options);
                    callback.setView(imageView);
                    callback.onFileLoaded((Bitmap) bitmap, LoadedFrom.NETWORK);
                }
            }
        });
    }

    public void load(final String urlString, final MindValleyDownloader.LoadJson getJsonResponse) {
        if (urlString == null || urlString == "")
            return;

        load(urlString, new CacheManager.CacheManagerStringCallback() {
            @Override
            public void onStringLoaded(final Object object, final LoadedFrom source) {
                if (object == null) {
                    final FileProcessorCallback callback = new FileProcessorCallback(fileManager, urlString);

                    try {
                        OkHttp3Downloader.getInstance().load(urlString, new OkHttp3Downloader.GetJsonCallback() {
                            @Override
                            public void onDownloadString(String json) {
                                if (json == null) {
                                    callback.onLoadFailed(LoadedFrom.NETWORK, new Exception("binaryData == null"));
                                }
                                getJsonResponse.onJsonLoaded(callback.onJsonLoaded(json, LoadedFrom.NETWORK));

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    final FileProcessorCallback callback = new FileProcessorCallback(fileManager, urlString);
                    getJsonResponse.onJsonLoaded(callback.onJsonLoaded((String) object, LoadedFrom.NETWORK));
                }
            }
        });
//        return "";
    }


    private void load(final String urlString, final View view, final JobOptions options, final CacheManager.CacheManagerCallback callback) {
        FileManager.runningJobs.put(view, urlString);

        if (view instanceof ImageView) {
            if (options.getPlaceholderResId() != NO_PLACEHOLDER)
                CustomDrawable.setPlaceholder((ImageView) view, options.getPlaceholderResId(), null);
            FileManager.getDefaultCacheManager().get(urlString, callback);
        }
    }

    private void load(final String urlString, final CacheManager.CacheManagerStringCallback callback) {
//        FileManager.runningJobs.put(view, urlString);
//
        FileManager.getDefaultCacheManager().get(urlString, callback);
    }

    private void work(final String url, final ImageView imageView, final JobOptions options) {
        final FileProcessorCallback callback = new FileProcessorCallback(fileManager, url);
        callback.setView(imageView);
        callback.setOptions(options);
        FileProcessor.decodeSampledBitmapFromRemoteUrl(context, url, options, callback);
    }

//    private String work(final String url) {
//
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


    public interface LoadJson {
        void onJsonLoaded(String jsonObject);
    }
}
