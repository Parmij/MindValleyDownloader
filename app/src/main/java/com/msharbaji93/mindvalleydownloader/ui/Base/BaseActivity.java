package com.msharbaji93.mindvalleydownloader.ui.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.msharbaji93.mindvalleydownloader.Application;
import com.msharbaji93.mindvalleydownloader.injection.component.ActivityComponent;
//import com.msharbaji93.mindvalleydownloader.injection.component.DaggerActivityComponent;
import com.msharbaji93.mindvalleydownloader.injection.component.DaggerActivityComponent;
import com.msharbaji93.mindvalleydownloader.injection.module.ActivityModule;


/**
 * Created by MHDSHA on 09/07/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(Application.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }
}
