package com.laaptu.baking.ui.recipedetail.steps.detail.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.laaptu.baking.R;
import com.laaptu.baking.common.ui.AutoInjectActivity;
import com.laaptu.baking.data.models.Step;
import com.laaptu.baking.ui.recipedetail.steps.detail.data.RecipeSteps;

import org.parceler.Parcels;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

import static com.laaptu.baking.utils.Extras.RECIPE_STEPS;

public class StepDetailActivity extends AutoInjectActivity {

    public static Intent getLaunchingIntent(Context context, String recipeName, List<Step> steps,
                                            int selectedStep) {
        Intent intent = new Intent(context, StepDetailActivity.class);
        RecipeSteps recipeSteps = new RecipeSteps(recipeName, steps, selectedStep);
        intent.putExtra(RECIPE_STEPS, Parcels.wrap(recipeSteps));
        return intent;
    }

    public static RecipeSteps getRecipeStepsFromIntent(Intent intent) {
        RecipeSteps recipeSteps = null;
        if (intent != null && intent.hasExtra(RECIPE_STEPS)) {
            recipeSteps = Parcels.unwrap(intent.getParcelableExtra(RECIPE_STEPS));
        }
        return recipeSteps;
    }

    private RecipeSteps recipeSteps;

    @BindView(R.id.viewpager_steps)
    ViewPager viewPagerSteps;
    @BindView(R.id.tab_layout_steps) TabLayout tabLayoutSteps;

    @Override public int getLayoutId() {
        return R.layout.activity_step_detail;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeSteps = getRecipeSteps(savedInstanceState);
        if (recipeSteps == null) {
            showToast(getString(R.string.error_no_recipe_steps));
            finish();
            return;
        }
        getSupportActionBar().setTitle(recipeSteps.recipeName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViews();
    }

    private RecipeSteps getRecipeSteps(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPE_STEPS)) {
            return Parcels.unwrap(savedInstanceState.getParcelable(RECIPE_STEPS));
        }
        return getRecipeStepsFromIntent(getIntent());
    }

    private void setupViews() {
        StepDetailPagerAdapter stepDetailPagerAdapter = new StepDetailPagerAdapter(
                this, recipeSteps.steps, getSupportFragmentManager());
        viewPagerSteps.setAdapter(stepDetailPagerAdapter);
        tabLayoutSteps.setupWithViewPager(viewPagerSteps);
        viewPagerSteps.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar()
                            .setSubtitle(recipeSteps.steps.get(position).shortDescription);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerSteps.setCurrentItem(recipeSteps.selectedStep);
    }

    @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(RECIPE_STEPS, Parcels.wrap(recipeSteps));
        super.onSaveInstanceState(outState);
    }
}
