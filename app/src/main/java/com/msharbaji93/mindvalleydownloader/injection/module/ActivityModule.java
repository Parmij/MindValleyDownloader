package com.msharbaji93.mindvalleydownloader.injection.module;

import android.app.Activity;
import android.content.Context;

import com.msharbaji93.mindvalleydownloader.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MHDSHA on 09/07/2017.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
