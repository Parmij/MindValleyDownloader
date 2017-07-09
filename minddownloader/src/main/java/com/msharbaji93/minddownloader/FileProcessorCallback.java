package com.msharbaji93.minddownloader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONObject;

/**
 * Created by MHDSHA on 08/07/2017.
 */

public class FileProcessorCallback implements FileManagerCallback {

    private static final String TAG = "ProcessorCallback";

    private final FileManager fileManager;
    private final String url;
    private View view;
    private JobOptions options;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public JobOptions getOptions() {
        return options;
    }

    public void setOptions(JobOptions options) {
        this.options = options;
    }

    private static final Handler uiHandler = new Handler(Looper.getMainLooper());

    public FileProcessorCallback(FileManager fileManager, String url) {
        this.url = url;
        this.fileManager = fileManager;
    }

    @Override
    public void onFileLoaded(Bitmap bitmap, final LoadedFrom source) {
        if (bitmap == null) {
            Log.e(TAG, "queueJob for urlString null");
            return;
        }

        fileManager.getDefaultCacheManager().put(url, bitmap);

        final String cachedUrl = fileManager.getRunningJobs().get(view);

        if (cachedUrl != null && cachedUrl.equals(url)) {
            options.setFadeIn(true);
            setImageDrawable((ImageView) view, bitmap, options, source);
        }
    }


    @Override
    public String onJsonLoaded(String jsonObject, final LoadedFrom source) {
        if (jsonObject == null) {
            Log.e(TAG, "queueJob for urlString null");
            return "";
        }

        fileManager.getDefaultCacheManager().put(url, jsonObject);
        Log.e("ssssssssss", "rrrrrrrr");
        return jsonObject;

//        final String cachedUrl = fileManager.getRunningJobs().get(view);

//        if (cachedUrl != null && cachedUrl.equals(url)) {
//            options.setFadeIn(true);
//            setImageDrawable((ImageView) view, bitmap, options, source);
//        }
    }


    @Override
    public void onLoadFailed(LoadedFrom source, Exception e) {
        Log.e(TAG, e.getMessage());
    }

    private void setImageDrawable(final ImageView imageView, Bitmap bitmap, final JobOptions options, final LoadedFrom loadedFrom) {
        final int targetWidth = imageView.getMeasuredWidth();
        final int targetHeight = imageView.getMeasuredHeight();
        if (targetWidth != 0 && targetHeight != 0) {
            options.setRequestedWidth(targetWidth);
            options.setRequestedHeight(targetHeight);
        }

        // Process the transformed (smaller) image
        final FileProcessor processor = new FileProcessor(fileManager.getContext());
        Bitmap processedBitmap = null;

        if (options.isRoundedCorners())
            processedBitmap = processor.getRoundedCorners(bitmap, options.getRadius());
        else if (options.isCircle())
            processedBitmap = processor.getCircle(bitmap);

        if (processedBitmap != null)
            bitmap = processedBitmap;

        final Bitmap finalBitmap = bitmap;

        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                CustomDrawable.setBitmap(imageView, fileManager.getContext(), finalBitmap, loadedFrom, !options.isFadeIn(), true);

                final FileManager.ViewCallback imageViewCallback = fileManager.getViewCallback();

                if (imageViewCallback != null)
                    imageViewCallback.onImageLoaded(imageView, finalBitmap);
            }
        });
    }
}
