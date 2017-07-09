package com.msharbaji93.mindvalleydownloader.ui.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.msharbaji93.minddownloader.JobOptions;
import com.msharbaji93.minddownloader.MindValleyDownloader;
import com.msharbaji93.minddownloader.ScaleType;
import com.msharbaji93.minddownloader.Utils;
import com.msharbaji93.mindvalleydownloader.R;
import com.msharbaji93.mindvalleydownloader.data.model.Photo;

import java.util.ArrayList;

/**
 * Created by MHDSHA on 09/07/2017.
 */

public class GridViewAdapter extends BaseAdapter {

    private static final float imageSizeRatio = 0.8f;
    private final ArrayList<Photo> urls;
    private final Context context;
    private final MindValleyDownloader mindValleyDownloader;
    private final JobOptions jobOptions;
    private static int imgWidth;
    private static int imgHeight;

    public GridViewAdapter(Context context, final ArrayList<Photo> urls) {
        this.context = context;
        this.urls = urls;
        mindValleyDownloader = new MindValleyDownloader(context);
        imgHeight = Utils.dpToPx(context, 130);
//        imgWidth = Utils.dpToPx(context, 200);
        jobOptions = new JobOptions();
        jobOptions.setPlaceholderResId(R.color.placeholder);
        jobOptions.setScaleType(ScaleType.CENTER_CROP);
        jobOptions.setRequestedHeight(imgHeight);
        jobOptions.setRequestedWidth(imgWidth);

    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getImageHeight((GridView)parent)));
        } else {
            imageView = (ImageView)convertView;
        }
        jobOptions.setPlaceholderResId(Color.parseColor(urls.get(position).getPlaceholderColor()));
        mindValleyDownloader.load(urls.get(position).getUrl(), imageView, jobOptions);

        return imageView;
    }

    @SuppressLint("NewApi")
    private int getImageHeight(final GridView gridView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            return (int)(gridView.getColumnWidth() * imageSizeRatio);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            return (int)((getScreenWidth() / gridView.getNumColumns()) * imageSizeRatio);

        return imgHeight;
    }

    public int getScreenWidth() {
        if (context == null)
            return 0;

        return getDisplayMetrics().widthPixels;
    }

    public DisplayMetrics getDisplayMetrics() {
        final WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
