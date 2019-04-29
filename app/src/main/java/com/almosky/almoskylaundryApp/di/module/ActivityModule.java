package com.almosky.almoskylaundryApp.di.module;


import com.almosky.almoskylaundryApp.common.BaseActivity;

import dagger.Module;

@Module
public class ActivityModule {
    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

}
