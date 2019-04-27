package com.laaptu.baking.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laaptu.baking.BakingApplication;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    public static Context getAppContext() {
        return BakingApplication.getAppContext();
    }

    @Provides
    public static Gson getGson() {
        return new GsonBuilder()
            .create();
    }

    @Singleton
    @Provides
    public static Bus getBus() {
        return new Bus();
    }

}
