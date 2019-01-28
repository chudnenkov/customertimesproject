package com.example.onexero.customertimesproject.di.module;

import android.app.Application;
import android.util.Log;

import com.example.onexero.customertimesproject.CustomApplicaton;
import com.example.onexero.customertimesproject.api.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private CustomApplicaton mApplication;

    public ApplicationModule(CustomApplicaton application) {
        this.mApplication = application;
    }

    @Provides
    CustomApplicaton provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Api providesApi() {
        return (new Retrofit.Builder()
                .baseUrl("http://demo5745870.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()).create(Api.class);
    }
}
