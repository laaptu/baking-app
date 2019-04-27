package com.laaptu.baking.ui.recipedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.laaptu.baking.R;
import com.laaptu.baking.common.ui.AutoInjectActivity;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.data.models.Step;
import com.laaptu.baking.ui.recipedetail.steps.RecipeStepsAdapter;
import com.laaptu.baking.ui.recipedetail.steps.detail.presentation.StepDetailFragment;
import com.laaptu.baking.ui.recipedetail.steps.detail.presentation.StepDetailActivity;
import com.laaptu.baking.ui.recipedetail.steps.list.data.StepClickedItem;
import com.laaptu.baking.ui.recipeslist.SpacingDecorator;
import com.laaptu.baking.ui.recipeslist.SpacingDecorator.Arrangement;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.laaptu.baking.utils.Extras.RECIPE;

public class RecipeDetailActivity extends AutoInjectActivity {

    private static final String SELECTED_STEP_POSITION = "selected-step-position";
    private int selectedStepPosition = 0;

    public static Intent getLaunchingIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RECIPE, Parcels.wrap(recipe));
        return intent;
    }

    public static Recipe getRecipeFromIntent(Intent intent) {
        Recipe recipe = null;
        if (intent != null && intent.hasExtra(RECIPE)) {
            recipe = Parcels.unwrap(intent.getParcelableExtra(RECIPE));
        }
        return recipe;
    }

    @BindView(R.id.rv_recipe_steps) RecyclerView rvRecipeSteps;
    private Recipe recipe;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = getRecipeFromIntent(getIntent());
        if (recipe == null) {
            showToast(getString(R.string.error_recipe_not_provided));
            finish();
            return;
        }
        getSupportActionBar().setTitle(recipe.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showRecipeSteps();
        restoreSelectedStepPositionForTablet(savedInstanceState);
    }

    private void showRecipeSteps() {
        rvRecipeSteps.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvRecipeSteps.addItemDecoration(new SpacingDecorator((int) getResources()
                .getDimension(R.dimen.item_space), Arrangement.VERTICAL));
        rvRecipeSteps.setAdapter(new RecipeStepsAdapter(recipe.steps,
                recipe.ingredients,
                eventBus));
    }

    private void restoreSelectedStepPositionForTablet(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_STEP_POSITION)) {
            selectedStepPosition =
                    savedInstanceState.getInt(SELECTED_STEP_POSITION, selectedStepPosition);
        }
        setSelectedPosition(selectedStepPosition);
    }

    @Subscribe
    public void onStepClicked(StepClickedItem stepClickedItem) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            setSelectedPosition(stepClickedItem.position);
        } else {
            startActivity(StepDetailActivity.getLaunchingIntent(
                    this, recipe.name, recipe.steps, stepClickedItem.position));
        }
    }

    private void setSelectedPosition(int selectedStepPosition) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            this.selectedStepPosition = selectedStepPosition;
            Step selectedStep = recipe.steps.get(selectedStepPosition);
            StepDetailFragment stepDetailFragment = StepDetailFragment.getInstance(selectedStep);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.step_detail_container, stepDetailFragment).commit();
            getSupportActionBar().setSubtitle(selectedStep.shortDescription);
        }
    }

    @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_STEP_POSITION, selectedStepPosition);
        super.onSaveInstanceState(outState);
    }
}
