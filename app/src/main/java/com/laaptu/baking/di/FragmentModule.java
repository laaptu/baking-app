package com.laaptu.baking.di;

import com.laaptu.baking.ui.recipedetail.steps.detail.presentation.StepDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract StepDetailFragment bindStepDetailFragment();
}
