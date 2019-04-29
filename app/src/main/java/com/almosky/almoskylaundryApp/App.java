package com.almosky.almoskylaundryApp;


import android.app.Application;


import com.almosky.almoskylaundryApp.di.component.ActivityComponent;
import com.almosky.almoskylaundryApp.di.component.AppComponent;
import com.almosky.almoskylaundryApp.di.component.DaggerActivityComponent;
import com.almosky.almoskylaundryApp.di.component.DaggerAppComponent;
import com.almosky.almoskylaundryApp.di.module.ApplicationModule;
import com.almosky.almoskylaundryApp.receiver.ConnectionReceiver;

import net.danlew.android.joda.JodaTimeAndroid;


public class App extends Application {

    private AppComponent mAppComponent;
    private ActivityComponent activityComponent;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

//        ViewTarget.setTagId(R.id.glide_tag);

        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirNext-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());  */
     /*   CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/CircularStd-Medium.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()); */
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(mAppComponent).build();
        mInstance = this;

    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }
}
