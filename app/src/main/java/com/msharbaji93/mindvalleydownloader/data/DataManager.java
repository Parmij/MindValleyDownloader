package com.msharbaji93.mindvalleydownloader.data;

import com.msharbaji93.mindvalleydownloader.data.network.ApiHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by MHDSHA on 09/07/2017.
 */

@Singleton
public class DataManager {

    private final ApiHelper apiHelper;

    @Inject
    public DataManager(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    public ApiHelper getApiHelper() {
        return apiHelper;
    }
}
