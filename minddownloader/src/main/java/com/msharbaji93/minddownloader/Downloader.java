package com.msharbaji93.minddownloader;

import android.support.annotation.NonNull;
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
    @NonNull Response load(@NonNull okhttp3.Request request) throws IOException;

    /**
     * Download the specified file {@code url} from the internet.
     *
     * @throws IOException if the requested URL cannot successfully be loaded.
     */
    @NonNull Response load(@NonNull String url) throws IOException;

}
