package com.msharbaji93.minddownloader;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MHDSHA on 05/07/2017.
 */

public class OkHttp3Downloader implements Downloader {

    private static final OkHttp3Downloader instance = new OkHttp3Downloader();

    @VisibleForTesting
    final OkHttpClient client;

    public OkHttp3Downloader() {
        client = new OkHttpClient();
    }

    public OkHttp3Downloader getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public Response load(@NonNull Request request) throws IOException {
        return client.newCall(request).execute();
    }

    @NonNull
    @Override
    public Response load(@NonNull String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return load(request);
    }


}
