package com.laaptu.baking.ui.recipedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.laaptu.baking.R;
import com.laaptu.baking.common.AutoInjectActivity;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.ui.recipedetail.steps.RecipeStepsAdapter;
import com.laaptu.baking.ui.recipeslist.RecipesListActivity;
import com.laaptu.baking.ui.recipeslist.SpacingDecorator;
import com.laaptu.baking.ui.recipeslist.SpacingDecorator.Arrangement;
import com.laaptu.baking.utils.GeneralUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

public class RecipeDetailActivity extends AutoInjectActivity {

    private static final String RECIPE = "recipe";

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
    }

    private void showRecipeSteps() {
        rvRecipeSteps.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvRecipeSteps.addItemDecoration(new SpacingDecorator((int) getResources()
                .getDimension(R.dimen.item_space), Arrangement.VERTICAL));
        rvRecipeSteps.setAdapter(new RecipeStepsAdapter(recipe.steps,
                recipe.ingredients,
                eventBus));
    }


}
