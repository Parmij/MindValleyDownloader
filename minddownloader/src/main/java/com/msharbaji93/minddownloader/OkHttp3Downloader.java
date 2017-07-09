package com.msharbaji93.minddownloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by MHDSHA on 05/07/2017.
 */

public class OkHttp3Downloader implements Downloader {

    private static final OkHttp3Downloader instance = new OkHttp3Downloader();
    private final OkHttpClient client;


    public OkHttp3Downloader() {
        client = new OkHttpClient();
    }

    public OkHttp3Downloader(Context context) {
        client = new OkHttpClient();
    }

    public static OkHttp3Downloader getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public void load(@NonNull final Request request, final DownoadFileCallback downoadFileCallback) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    InputStream is = response.body().byteStream();
                    byte[] arr = IOUtils.toByteArray(is);
                    if (arr != null)
                        downoadFileCallback.onDownloadFile(arr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @NonNull
    @Override
    public void load(@NonNull final Request request, final GetJsonCallback getJsonCallback) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    getJsonCallback.onDownloadString(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @NonNull
    @Override
    public void load(@NonNull String url, final DownoadFileCallback downoadFileCallback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        load(request, downoadFileCallback);
    }

    @NonNull
    @Override
    public void load(@NonNull String url, final GetJsonCallback getJsonCallback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        load(request, getJsonCallback);
    }

    public interface DownoadFileCallback {
        void onDownloadFile(byte[] binaryData);
    }

    public interface GetJsonCallback {
        void onDownloadString(String json);
    }

}
