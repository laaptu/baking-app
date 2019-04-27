package com.laaptu.baking.ui.recipedetail.steps.detail.di;

import com.laaptu.baking.ui.recipedetail.steps.detail.StepDetailFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class StepDetailFragmentModule {
    @Provides StepDetailFragment provideStepDetailFragment(StepDetailFragment stepDetailFragment){
        return stepDetailFragment;
    }
}
