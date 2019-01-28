package com.example.onexero.customertimesproject.di.component;

import android.app.Application;

import com.example.onexero.customertimesproject.CustomApplicaton;
import com.example.onexero.customertimesproject.api.Api;
import com.example.onexero.customertimesproject.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
    void inject(CustomApplicaton application);

    CustomApplicaton application();
    Api getApi();
}
