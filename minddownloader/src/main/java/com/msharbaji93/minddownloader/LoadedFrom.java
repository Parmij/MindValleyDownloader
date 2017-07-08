package com.msharbaji93.minddownloader;

import android.graphics.Color;

/**
 * Created by MHDSHA on 08/07/2017.
 */

public enum  LoadedFrom {
    MEMORY(Color.GREEN), DISK(Color.YELLOW), NETWORK(Color.RED), LOCAL(Color.BLUE);

    final int debugColor;

    private LoadedFrom(final int debugColor) {
        this.debugColor = debugColor;
    }
}
