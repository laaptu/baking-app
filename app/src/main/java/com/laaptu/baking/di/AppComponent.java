package com.laaptu.baking.di;

import com.laaptu.baking.BakingApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        FragmentModule.class,
        AppModule.class,
        NetworkModule.class
})
@Singleton
public interface AppComponent {
    void inject(BakingApplication bakingApplication);
}
