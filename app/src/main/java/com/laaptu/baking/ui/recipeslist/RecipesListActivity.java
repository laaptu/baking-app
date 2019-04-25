package com.laaptu.baking.ui.recipeslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.laaptu.baking.R;
import com.laaptu.baking.common.AutoInjectActivity;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.utils.GeneralUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import static com.laaptu.baking.utils.GeneralUtils.isRecipesValid;

public class RecipesListActivity extends AutoInjectActivity {

    private static final String RECIPES = "recipes";

    public static Intent getLaunchingIntent(Context context, List<Recipe> recipes) {
        Intent intent = new Intent(context, RecipesListActivity.class);
        intent.putExtra(RECIPES, Parcels.wrap(recipes));
        return intent;
    }

    public static List<Recipe> getRecipesFromIntent(Intent intent) {
        List<Recipe> recipes = new ArrayList<>();
        if (intent != null && intent.hasExtra(RECIPES)) {
            recipes = intent.getParcelableExtra(RECIPES);
        }
        return recipes;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recipes_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(true)
            return;
        List<Recipe> recipes = getRecipesFromIntent(getIntent());
        if (!isRecipesValid(recipes)) {
            showToast(getString(R.string.error_displaying_recipes));
            finish();
            return;
        }
    }
}
