package com.msharbaji93.mindvalleydownloader.injection.component;

import com.msharbaji93.mindvalleydownloader.injection.PerActivity;
import com.msharbaji93.mindvalleydownloader.injection.module.ActivityModule;
import com.msharbaji93.mindvalleydownloader.ui.Main.MainActivity;

import dagger.Component;

/**
 * Created by MHDSHA on 09/07/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
