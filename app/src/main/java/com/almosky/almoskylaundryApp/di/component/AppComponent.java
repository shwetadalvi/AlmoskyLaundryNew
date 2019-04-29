package com.almosky.almoskylaundryApp.di.component;

import android.content.Context;


import com.almosky.almoskylaundryApp.di.module.ApplicationModule;
import com.almosky.almoskylaundryApp.utils.AppPrefes;
import com.almosky.almoskylaundryApp.utils.UtilsPref;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class})
public interface AppComponent {
    Context appContext();

    UtilsPref appUtilsPref();

    AppPrefes appAppPrefes();

    ApiCalls appApiCalls();
}
