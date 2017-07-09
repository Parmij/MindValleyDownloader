package com.msharbaji93.minddownloader;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * Created by MHDSHA on 07/07/2017.
 */

public interface FileManagerCallback {

    void onFileLoaded(final Bitmap object, final LoadedFrom source);

    String onJsonLoaded(final String object, final LoadedFrom source);

    void onLoadFailed(final LoadedFrom source, Exception e);
}
