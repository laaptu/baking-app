package com.laaptu.baking.ui.recipedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.laaptu.baking.R;
import com.laaptu.baking.common.AutoInjectActivity;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.ui.recipeslist.RecipesListActivity;
import com.laaptu.baking.utils.GeneralUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

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

    private Recipe recipe;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recipes_list;
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
    }


}
