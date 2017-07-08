package com.msharbaji93.minddownloader;

/**
 * Created by MHDSHA on 07/07/2017.
 */

public enum ScaleType {
    NONE(-1), FIT_XY(1), FIT_CENTER(3), CENTER_CROP(6);

    ScaleType(final int ni) {
        nativeInt = ni;
    }

    final int nativeInt;
}
