package com.laaptu.baking.ui.recipedetail.steps.detail.di;

import com.laaptu.baking.ui.recipedetail.steps.detail.StepDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StepDetailFragmentProvider {
    @ContributesAndroidInjector(modules = StepDetailFragmentModule.class)
    abstract StepDetailFragment bindStepDetailFragment();
}
