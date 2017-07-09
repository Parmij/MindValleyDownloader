package com.msharbaji93.mindvalleydownloader.injection.module;

import android.app.Application;
import android.content.Context;

import com.msharbaji93.mindvalleydownloader.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MHDSHA on 09/07/2017.
 */

@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }
}
