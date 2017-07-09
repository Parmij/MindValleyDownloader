package com.msharbaji93.mindvalleydownloader.data.network;

import android.content.Context;

import com.msharbaji93.minddownloader.MindValleyDownloader;
import com.msharbaji93.mindvalleydownloader.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by MHDSHA on 09/07/2017.
 */

@Singleton
public class ApiHelper {

    MindValleyDownloader mindValleyDownloader;

    @Inject
    public ApiHelper(){

    }

    public void getJsonContent (Context context, String url, MindValleyDownloader.LoadJson callback){
        MindValleyDownloader mindValleyDownloader = new MindValleyDownloader(context);
        mindValleyDownloader.load(url, callback);
}
}
