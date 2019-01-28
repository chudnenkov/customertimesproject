package com.example.onexero.customertimesproject.di.component;

import com.example.onexero.customertimesproject.di.module.ActivityModule;
import com.example.onexero.customertimesproject.di.module.ApplicationModule;
import com.example.onexero.customertimesproject.di.scope.ActivityScope;
import com.example.onexero.customertimesproject.view.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}
