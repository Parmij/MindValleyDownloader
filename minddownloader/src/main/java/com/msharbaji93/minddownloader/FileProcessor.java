package com.msharbaji93.minddownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MHDSHA on 08/07/2017.
 */

public class FileProcessor {

    private static final String TAG = "FileProcessor";

    /**
     * Method to calculate SampleSize (used in calculateSampleSize)
     */
    public enum SamplingMethod {
        /**
         * Choose the smallest ratio as inSampleSize value, this will guarantee a final image
         * with both dimensions larger than or equal to the requested height and width.
         * http://developer.android.com/training/displaying-bitmaps/load-bitmap.html#load-bitmap
         */
        STANDARD,

        /**
         * Choose the largest ratio as inSampleSize value, this will guarantee a final image
         * with both dimensions smaller than or equal to the requested height and width.
         */
        WITHINREQ,
    }

    ;

    private final Context context;

    public FileProcessor(final Context context) {
        this.context = context;
    }

    public Bitmap getRoundedCorners(final Bitmap bitmap, final int radius) {
        Bitmap output = null;
        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        } catch (final OutOfMemoryError e) {
            Log.e(TAG, "Out of memory in getRoundedCorners()");
            return null;
        }
        final Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public Bitmap getAvatarThumbnail(final Bitmap b) {
        Bitmap finalBitmap;
        if (b.getHeight() > b.getWidth()) {
            finalBitmap = Bitmap.createBitmap(b, 0, (b.getHeight() - b.getWidth()) / 2, b.getWidth(), b.getWidth());
        } else if (b.getHeight() < b.getWidth()) {
            finalBitmap = Bitmap.createBitmap(b, (b.getWidth() - b.getHeight()) / 2, 0, b.getHeight(), b.getHeight());
        } else {
            finalBitmap = b;
        }
        finalBitmap = Bitmap.createScaledBitmap(finalBitmap, Utils.dpToPx(context, 50), Utils.dpToPx(context, 50), true);

        final int radius = (int) (finalBitmap.getHeight() * 0.07f);

        return getRoundedCorners(finalBitmap, radius);
    }

    public static int calculateSampleSize(final BitmapFactory.Options options, final int reqWidth, final int reqHeight, final SamplingMethod samplingMethod) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            switch (samplingMethod) {
                case STANDARD: {
                    // Standard method found at http://developer.android.com/training/displaying-bitmaps/load-bitmap.html#load-bitmap
                    // Calculate ratios of height and width to requested height and width
                    final int heightRatio = Math.round((float) height / (float) reqHeight);
                    final int widthRatio = Math.round((float) width / (float) reqWidth);

                    // Choose the smallest ratio as inSampleSize value, this will guarantee
                    // a final image with both dimensions larger than or equal to the
                    // requested height and width.
                    inSampleSize = Math.min(widthRatio, heightRatio);
                    break;
                }
                case WITHINREQ: {
                    // Calculate ratios of height and width to requested height and width
                    final float heightRatio = (float) height / (float) reqHeight;
                    final float widthRatio = (float) width / (float) reqWidth;

                    // Choose the largest ratio as inSampleSize value, this will guarantee
                    // a final image with both dimensions smaller than or equal to the
                    // requested height and width.
                    inSampleSize = (int) Math.ceil(Math.max(widthRatio, heightRatio));
                    break;
                }
            }
        }

        return inSampleSize;
    }

    public Bitmap getCircle(final Bitmap bitmap) {
        Bitmap output = null;
        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        } catch (final OutOfMemoryError e) {
            Log.e(TAG, "Out of memory in getCircle()");
            return null;
        }
        final Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * Calculate max sampleSize logarithmically to obtain a ^2 sample
     * <p>
     * TODO: Needs to be tested a bit more (create Unit Test for this)
     *
     * @param options
     * @param bounds
     * @return sampleSize
     */
    public static int calculateSampleSizeLog(final BitmapFactory.Options options, final int bounds) {
        // Figure out the minimum size to fit within the bounds
        final int ow = options.outWidth, oh = options.outHeight;
        final double delta = ow > bounds ? ow - bounds : oh - bounds;
        final double targetWidth = ow - delta, targetHeight = oh - delta;
        // Figure out max factor sampling within intended bounds
        final boolean scaleByHeight = Math.abs(oh - targetHeight) >= Math.abs(ow - targetWidth);
        final double sampleSize = Math.ceil(scaleByHeight ? oh / targetHeight : ow / targetWidth);
        // Apply logarithm to get the sample closest to its ^2
        final int inSampleSize = (int) Math.pow(2d, Math.floor(Math.log(sampleSize) / Math.log(2d)));

        return inSampleSize;
    }

    /**
     * Write a compressed version of the bitmap to the specified outputstream.
     * If this returns true, the bitmap can be reconstructed by passing a
     * corresponding inputstream to BitmapFactory.decodeStream(). Note: not
     * all Formats support all bitmap configs directly, so it is possible that
     * the returned bitmap from BitmapFactory could be in a different bitdepth,
     * and/or may have lost per-pixel alpha (e.g. JPEG only supports opaque
     * pixels).
     *
     * @param bitmap  Bitmap to compress
     * @param format  The format of the compressed image
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for
     *                small size, 100 meaning compress for max quality. Some
     *                formats, like PNG which is lossless, will ignore the
     *                quality setting
     * @return ByteArrayInputStream with compressed bitmap
     */
    public static ByteArrayInputStream compress(final Bitmap bitmap, final Bitmap.CompressFormat format, final int quality) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(format, quality, outputStream);
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            outputStream.close();
            return inputStream;
        } catch (final IOException e) {
            Log.e(TAG, "compress", e);
            return null;
        }
    }

    public static Bitmap decodeStream(final InputStream stream) {
        try {
            return BitmapFactory.decodeStream(stream);
        } catch (final OutOfMemoryError e) {
            Log.e(TAG, "Out of memory in decodeStream()", e);
            return null;
        }
    }

    public static void decodeSampledBitmapFromRemoteUrl(final Context context,
                                                        final String urlString,
                                                        final JobOptions options,
                                                        final FileManagerCallback callback) {

        decodeSampledBitmapFromRemoteUrl(context, urlString, options, SamplingMethod.STANDARD, callback);
    }

    /**
     * Decodes a sampled Bitmap from the provided url in the requested width and height
     *
     * @param urlString URL to download the bitmap from
     */
    public static void decodeSampledBitmapFromRemoteUrl(final Context context,
                                                        final String urlString,
                                                        final JobOptions jobOptions,
                                                        final SamplingMethod samplingMethod,
                                                        final FileManagerCallback callback) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;

        try {
            OkHttp3Downloader.getInstance().load(urlString, new OkHttp3Downloader.DownoadFileCallback() {
                @Override
                public void onDownloadFile(byte[] binaryData) {
                    if (binaryData == null) {
                        callback.onLoadFailed(LoadedFrom.NETWORK, new Exception("binaryData == null"));
                        return;
                    }

                    decodeByteArray(binaryData, options);
//
                    int width = jobOptions.getRequestedWidth();
                    int height = jobOptions.getRequestedHeight();

                    if (jobOptions.getRequestedWidth() == 0) {
                        width = options.outWidth;
                    }
                    if (jobOptions.getRequestedHeight() == 0) {
                        height = options.outHeight;
                    }
//
//                // Calculate inSampleSize
                    options.inSampleSize = calculateSampleSize(options, width, height, samplingMethod);
//
                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;

                    try {
                        OkHttp3Downloader.getInstance().load(urlString, new OkHttp3Downloader.DownoadFileCallback() {
                            @Override
                            public void onDownloadFile(byte[] binaryData) {

                                if (binaryData != null)
                                    callback.onFileLoaded(decodeByteArray(binaryData, options), LoadedFrom.NETWORK);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap decodeByteArray(final byte[] data, final BitmapFactory.Options options) {
        try {
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (final OutOfMemoryError e) {
            Log.e(TAG, "Out of memory in decodeByteArray()", e);
            return null;
        }
    }

    /**
     * Decodes Bitmap metadata without placing the actual raw bitmap in memory.
     *
     * @param context
     * @param imageUri
     * @return BitmapFactory.Options with metadata
     */
    public static BitmapFactory.Options decodeBitmapBounds(final Context context, final Uri imageUri) {
        try {
            return decodeBitmapBounds(context.getContentResolver().openInputStream(imageUri));
        } catch (final FileNotFoundException e) {
            Log.e(TAG, "decodeBitmapBounds(Context, Uri)", e);
            return null;
        }
    }

    /**
     * Decodes Bitmap metadata without placing the actual raw bitmap in memory.
     * <p>
     * If InputStream doesn't support mark/reset, it'll get closed afterwards.
     *
     * @param inputStream
     * @return BitmapFactory.Options with metadata
     */
    public static BitmapFactory.Options decodeBitmapBounds(final InputStream inputStream) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; // Only decode metadata, do not place bitmap in memory
            if (inputStream.markSupported()) {
                inputStream.mark(inputStream.available());
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.reset();
            } else {
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            }

            return options;

        } catch (final IOException e) {
            Log.e(TAG, "decodeBitmapBounds()", e);
            return null;
        }
    }

    public static InputStream compressToFit(final Context context, Bitmap bitmap, final int maximumSizeInBytes, final int pixelBounds,
                                            final Bitmap.CompressFormat format, final int quality) {
        InputStream inputStream = null;

        try {
            // If the bitmap is within our size bounds, try an initial compression
            if (bitmap.getWidth() <= pixelBounds && bitmap.getHeight() <= pixelBounds)
                inputStream = FileProcessor.compress(bitmap, format, quality);

            // Re-scale image if width > widthBound
            float ceil = (float) Math.ceil(pixelBounds * 1.333);
            int rWidth = bitmap.getWidth();
            int rHeight = bitmap.getHeight();

            // If inputStream is null (too big) or the compressed bitmap is too large, downscale
            if (inputStream == null || inputStream.available() > maximumSizeInBytes) {
                Log.i(TAG, "Image is too large" + (inputStream != null ? (" (" + inputStream.available() / 1024 + "kb)") : "") +
                        ", will be adjusted to " + pixelBounds + ", " + pixelBounds);

                do {
                    // Adjust our ceiling
                    ceil = (float) Math.ceil(ceil * .75f);
                    final int fCeil = (int) ceil;

                    Log.d(TAG, "compression attempt at " + fCeil + "x" + fCeil);

                    // Downscale bitmap
                    JobOptions options = new JobOptions();
                    options.setRequestedHeight(fCeil);
                    options.setRequestedHeight(fCeil);
                    options.setScaleType(ScaleType.FIT_CENTER);
                    Bitmap transformedBitmap = ImageUtil.transformBitmap(bitmap, options);

                    // Store some data we need to access beyond the do{} block
                    rWidth = transformedBitmap.getWidth();
                    rHeight = transformedBitmap.getHeight();

                    // If it's the same, something went wrong (out of mem), iterate here and try a smaller image
                    if (transformedBitmap.equals(bitmap))
                        continue;

                    // Recycle the Bitmap
                    bitmap.recycle();
                    bitmap = null;

                    // Compress to JPEG and get its input stream for uploading
                    if (inputStream != null)
                        inputStream.close();
                    inputStream = FileProcessor.compress(transformedBitmap, format, quality);

                    // Lets use the transformedBitmap as our next source
                    bitmap = transformedBitmap;
                    transformedBitmap = null;

                    // Yield to prevent the OS from killing us
                    Thread.yield();

                } while (inputStream.available() > maximumSizeInBytes);
            }

            Log.d(TAG, String.format("Resulting image of size: %d, %d - %dkb", rWidth, rHeight, inputStream != null ? (inputStream.available() / 1024) : 0));

        } catch (final Exception e) { // IOException
            Log.e(TAG, "", e);

        } finally {
            if (bitmap != null)
                bitmap.recycle();
        }

        return inputStream;
    }
}
