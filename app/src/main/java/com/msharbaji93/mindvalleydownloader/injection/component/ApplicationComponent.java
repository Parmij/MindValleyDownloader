package com.msharbaji93.mindvalleydownloader.injection.component;

import android.app.Application;
import android.content.Context;

import com.msharbaji93.mindvalleydownloader.data.DataManager;
import com.msharbaji93.mindvalleydownloader.injection.ApplicationContext;
import com.msharbaji93.mindvalleydownloader.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by MHDSHA on 09/07/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context ();

    Application application();

    DataManager dataManager();
}


