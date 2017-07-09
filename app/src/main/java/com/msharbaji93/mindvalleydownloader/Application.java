package com.msharbaji93.mindvalleydownloader;

import android.content.Context;

import com.msharbaji93.mindvalleydownloader.injection.component.ApplicationComponent;
//import com.msharbaji93.mindvalleydownloader.injection.component.DaggerApplicationComponent;
import com.msharbaji93.mindvalleydownloader.injection.component.DaggerApplicationComponent;
import com.msharbaji93.mindvalleydownloader.injection.module.ApplicationModule;

/**
 * Created by MHDSHA on 09/07/2017.
 */

public class Application extends android.app.Application {

    ApplicationComponent mApplicationComponent;


    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}