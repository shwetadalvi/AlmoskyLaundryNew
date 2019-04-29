package com.almosky.almoskylaundryApp.di.module;

import android.content.Context;

import com.almosky.almoskylaundryApp.App;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.UtilsPref;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;






@Module
public class ApplicationModule {

    private final App mApp;

    public ApplicationModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Context appContext() {
        return mApp;
    }

    @Provides
    @Singleton
    public UtilsPref appUtilsPref() {
        return new UtilsPref(mApp.getActivityComponent());
    }

    @Provides
    @Singleton
    public AppPrefes appAppPrefes() {
        return new AppPrefes(appContext().getApplicationContext());
    }

    @Provides
    @Singleton
    public ApiCalls appApiCalls() {
        return new ApiCalls();
    }
}
