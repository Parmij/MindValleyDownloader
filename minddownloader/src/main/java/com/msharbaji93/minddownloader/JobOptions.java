package com.msharbaji93.minddownloader;

import android.widget.ImageView;

/**
 * Created by MHDSHA on 07/07/2017.
 */

public class JobOptions {
    private boolean roundedCorners = false;
    private boolean circle = false;
    private boolean fadeIn = false;

    // default: no scaling
    private ScaleType scaleType = ScaleType.NONE;

    private int radius = 5;
    private int requestedWidth;
    private int requestedHeight;

    private int placeholderResId = -1;

    // size bounds, 1024 or 2048, to avoid loading big images to imageViews
    private int bounds;

    public JobOptions() {
        this(0, 0);
    }

    public JobOptions(final ImageView imgView) {
        this(imgView.getWidth(), imgView.getHeight());
    }

    public JobOptions(final int requestedWidth, final int requestedHeight) {
        this.requestedWidth = requestedWidth;
        this.requestedHeight = requestedHeight;
    }

    public boolean isRoundedCorners() {
        return roundedCorners;
    }

    public void setRoundedCorners(boolean roundedCorners) {
        this.roundedCorners = roundedCorners;
    }

    public boolean isCircle() {
        return circle;
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }

    public boolean isFadeIn() {
        return fadeIn;
    }

    public void setFadeIn(boolean fadeIn) {
        this.fadeIn = fadeIn;
    }

    public ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRequestedWidth() {
        return requestedWidth;
    }

    public void setRequestedWidth(int requestedWidth) {
        this.requestedWidth = requestedWidth;
    }

    public int getRequestedHeight() {
        return requestedHeight;
    }

    public void setRequestedHeight(int requestedHeight) {
        this.requestedHeight = requestedHeight;
    }

    public int getBounds() {
        return bounds;
    }

    public void setBounds(int bounds) {
        this.bounds = bounds;
    }

    public int getPlaceholderResId() {
        return placeholderResId;
    }

    public void setPlaceholderResId(int placeholderResId) {
        this.placeholderResId = placeholderResId;
    }
}
