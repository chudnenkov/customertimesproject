package com.example.onexero.customertimesproject;

import android.app.Activity;
import android.app.Application;

import com.example.onexero.customertimesproject.di.component.ApplicationComponent;
import com.example.onexero.customertimesproject.di.component.DaggerApplicationComponent;
import com.example.onexero.customertimesproject.di.module.ApplicationModule;

public class CustomApplicaton extends Application {

    public static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);
    }

    public static CustomApplicaton get(Activity activity){
        return (CustomApplicaton) activity.getApplication();
    }
    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
