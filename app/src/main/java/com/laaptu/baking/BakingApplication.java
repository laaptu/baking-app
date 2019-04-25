package com.laaptu.baking;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.laaptu.baking.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class BakingApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        DaggerAppComponent.builder().build().inject(this);
        Timber.plant(new DebugTree());
    }

    public static Context getAppContext() {
        return appContext;
    }

}
