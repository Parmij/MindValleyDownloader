package com.msharbaji93.minddownloader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by MHDSHA on 08/07/2017.
 */

public class FileProcessorCallback implements FileManagerCallback {

    private static final String TAG = "ProcessorCallback";

    private final FileManager fileManager;
    private final String url;
    private final ImageView imageView;
    private final JobOptions options;

    private static final Handler uiHandler = new Handler(Looper.getMainLooper());

    public FileProcessorCallback(FileManager fileManager, String url, ImageView imageView, JobOptions options) {
        this.url = url;
        this.imageView = imageView;
        this.options = options;
        this.fileManager = fileManager;
    }

    @Override
    public void onFileLoaded(Bitmap bitmap, LoadedFrom source) {

        if (bitmap == null) {
            Log.e(TAG, "queueJob for urlString null");
            return;
        }

        fileManager.getCacheManager().put(FileManager.getCacheKeyForJob(url, options), bitmap);

        final String cachedUrl = fileManager.getRunningJobs().get(imageView);

        if (cachedUrl != null && cachedUrl.equals(url)) {
            options.fadeIn = true;
            setImageDrawable(imageView, bitmap, options, source);
        }
    }

    @Override
    public void onLoadFailed(LoadedFrom source, Exception e) {
        if (fileManager.getPlaceholderResId() != FileManager.NO_PLACEHOLDER) {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    CustomDrawable.setPlaceholder(imageView, fileManager.getPlaceholderResId(), null);
                }
            });
        }
    }

    private void setImageDrawable(final ImageView imageView, Bitmap bitmap, final JobOptions options, final LoadedFrom loadedFrom) {
        final int targetWidth = imageView.getMeasuredWidth();
        final int targetHeight = imageView.getMeasuredHeight();
        if (targetWidth != 0 && targetHeight != 0) {
            options.requestedWidth = targetWidth;
            options.requestedHeight = targetHeight;
        }

        // Process the transformed (smaller) image
        final FileProcessor processor = new FileProcessor(fileManager.getContext());
        Bitmap processedBitmap = null;

        if (options.roundedCorners)
            processedBitmap = processor.getRoundedCorners(bitmap, options.radius);
        else if (options.circle)
            processedBitmap = processor.getCircle(bitmap);

        if (processedBitmap != null)
            bitmap = processedBitmap;

        final Bitmap finalBitmap = bitmap;

        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                CustomDrawable.setBitmap(imageView, fileManager.getContext(), finalBitmap, loadedFrom, !options.fadeIn, true);

                final FileManager.ViewCallback imageViewCallback = fileManager.getViewCallback();

                if (imageViewCallback != null)
                    imageViewCallback.onImageLoaded(imageView, finalBitmap);
            }
        });
    }
}
