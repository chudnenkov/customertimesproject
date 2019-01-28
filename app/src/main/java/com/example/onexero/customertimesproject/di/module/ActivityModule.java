package com.example.onexero.customertimesproject.di.module;

import com.example.onexero.customertimesproject.CustomApplicaton;
import com.example.onexero.customertimesproject.api.Api;
import com.example.onexero.customertimesproject.presenter.Presenter;
import com.example.onexero.customertimesproject.view.IView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {
    private IView mIview;

    public ActivityModule(IView view){
        this.mIview = view;
    }

    @Provides
    IView ActivityModule(){
        return mIview;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    Presenter providesPresenter(Api api, IView view, CompositeDisposable compositeDisposable, CustomApplicaton applicaton){
        return new Presenter(api, view, compositeDisposable, applicaton);
    }
}
