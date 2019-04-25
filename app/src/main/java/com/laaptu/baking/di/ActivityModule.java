package com.laaptu.baking.di;

import com.laaptu.baking.ui.recipeslist.RecipesListActivity;
import com.laaptu.baking.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract RecipesListActivity bindRecipesListActivity();
}