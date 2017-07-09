package com.msharbaji93.mindvalleydownloader.ui.Main;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.msharbaji93.mindvalleydownloader.Application;
import com.msharbaji93.mindvalleydownloader.R;
import com.msharbaji93.mindvalleydownloader.data.model.Pastebin;
import com.msharbaji93.mindvalleydownloader.ui.Base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.gallaryGridView)
    GridView gallaryGridView;

    @Inject
    GridViewAdapter gridViewAdapter;

    ArrayList<Pastebin> pastebins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

    }

    public ArrayList<Pastebin> loadJson() {
        String content = Application.get(MainActivity.this).getComponent().dataManager().getApiHelper().getJsonContent("https://pastebin.com/raw/wgkJgazE");
        Gson gson = new Gson();
        pastebins = gson.fromJson(content, new TypeToken<ArrayList<Pastebin>>() {
        }.getType());
        Log.d("content", pastebins.size() + "");
        return pastebins;
    }

    public ArrayList<String> gatherImages() {
        ArrayList<String> imagesURL = new ArrayList<>();
        for (Pastebin pastebin : pastebins) {
            imagesURL.add(pastebin.getUrls().getRaw());
            imagesURL.add(pastebin.getUrls().getFull());
            imagesURL.add(pastebin.getUrls().getRegular());
            imagesURL.add(pastebin.getUrls().getThumb());
            imagesURL.add(pastebin.getUrls().getSmall());
            imagesURL.add(pastebin.getUser().getProfile_image().getSmall());
            imagesURL.add(pastebin.getUser().getProfile_image().getLarge());
            imagesURL.add(pastebin.getUser().getProfile_image().getMedium());
        }
        long seed = System.nanoTime();
        Collections.shuffle(imagesURL, new Random(seed));
        return imagesURL;
    }
}
