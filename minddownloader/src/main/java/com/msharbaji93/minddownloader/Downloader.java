package com.msharbaji93.minddownloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.io.IOException;
import okhttp3.Response;

/**
 * Created by Mohamad Alsharbaji on 05/07/2017.
 */

/** A mechanism to load file from external resources such as a disk cache and/or the internet. */
public interface Downloader {

    /**
     * Download the specified file {@code url} from the internet.
     *
     * @throws IOException if the requested URL cannot successfully be loaded.
     */
    @NonNull void load(@NonNull okhttp3.Request request,  final OkHttp3Downloader.DownoadFileCallback downoadFileCallback) throws IOException;

    /**
     * Download the specified file {@code url} from the internet.
     *
     * @throws IOException if the requested URL cannot successfully be loaded.
     */
    @NonNull void load(@NonNull String url , final OkHttp3Downloader.DownoadFileCallback downoadFileCallback) throws IOException;

}
