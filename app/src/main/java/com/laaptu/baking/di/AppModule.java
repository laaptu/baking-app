package com.laaptu.baking.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laaptu.baking.BakingApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    static Context getAppContext() {
        return BakingApplication.getAppContext();
    }

    @Provides
    static Gson getGson() {
        return new GsonBuilder()
            .create();
    }
}
