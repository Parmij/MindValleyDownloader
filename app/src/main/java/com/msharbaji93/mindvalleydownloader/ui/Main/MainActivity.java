package com.msharbaji93.mindvalleydownloader.ui.Main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.msharbaji93.minddownloader.MindValleyDownloader;
import com.msharbaji93.mindvalleydownloader.Application;
import com.msharbaji93.mindvalleydownloader.R;
import com.msharbaji93.mindvalleydownloader.data.model.Pastebin;
import com.msharbaji93.mindvalleydownloader.data.model.Photo;
import com.msharbaji93.mindvalleydownloader.ui.Base.BaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.gallaryGridView)
    GridView gallaryGridView;

    @BindView(R.id.swipe_refresh_gridview)
    SwipeRefreshLayout swipeAndRefresh;

    GridViewAdapter gridViewAdapter;

    ArrayList<Pastebin> pastebins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadJson();

        swipeAndRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               loadJson();
            }
        });
        // Configure the refreshing colors
        swipeAndRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    public void loadJson() {
        Application.get(MainActivity.this).getComponent().dataManager().getApiHelper().getJsonContent(MainActivity.this,"https://pastebin.com/raw/wgkJgazE", new MindValleyDownloader.LoadJson() {
            @Override
            public void onJsonLoaded(String jsonObject) {
                Gson gson = new Gson();
                String json = jsonObject.replaceAll("\r" ,"").replaceAll("\n" ,"").replaceAll("\t" ,"");
                pastebins = gson.fromJson(json, new TypeToken<ArrayList<Pastebin>>() {
                }.getType());
                ArrayList<Photo> imagesUrl = gatherImages();
                gridViewAdapter = new GridViewAdapter(MainActivity.this,imagesUrl);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gallaryGridView.setAdapter(gridViewAdapter);
                    }
                });

                swipeAndRefresh.setRefreshing(false);

                Log.d("content", pastebins.size() + "");
            }
        });
    }

    public ArrayList<Photo> gatherImages() {
        ArrayList<Photo> imagesURL = new ArrayList<>();
        Photo photo = null;
        for (Pastebin pastebin : pastebins) {
            photo = new Photo();
            photo.setPlaceholderColor(pastebin.getColor());
            photo.setUrl(pastebin.getUrls().getRaw());
            imagesURL.add(photo);

            photo = new Photo();
            photo.setPlaceholderColor(pastebin.getColor());
            photo.setUrl(pastebin.getUrls().getFull());
            imagesURL.add(photo);

            photo = new Photo();
            photo.setPlaceholderColor(pastebin.getColor());
            photo.setUrl(pastebin.getUrls().getRegular());
            imagesURL.add(photo);

            photo = new Photo();
            photo.setPlaceholderColor(pastebin.getColor());
            photo.setUrl(pastebin.getUrls().getThumb());
            imagesURL.add(photo);

            photo = new Photo();
            photo.setPlaceholderColor(pastebin.getColor());
            photo.setUrl(pastebin.getUrls().getSmall());
            imagesURL.add(photo);

            photo = new Photo();
            photo.setPlaceholderColor(pastebin.getColor());
            photo.setUrl(pastebin.getUser().getProfile_image().getSmall());
            imagesURL.add(photo);

            photo = new Photo();
            photo.setPlaceholderColor(pastebin.getColor());
            photo.setUrl(pastebin.getUser().getProfile_image().getLarge());
            imagesURL.add(photo);

            photo = new Photo();
            photo.setPlaceholderColor(pastebin.getColor());
            photo.setUrl(pastebin.getUser().getProfile_image().getMedium());
            imagesURL.add(photo);
        }
        long seed = System.nanoTime();
        Collections.shuffle(imagesURL, new Random(seed));
        return imagesURL;
    }
}
