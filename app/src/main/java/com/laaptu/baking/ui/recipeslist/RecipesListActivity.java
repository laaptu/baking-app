package com.laaptu.baking.ui.recipeslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.laaptu.baking.R;
import com.laaptu.baking.common.AutoInjectActivity;
import com.laaptu.baking.common.image.ImageLoader;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.ui.recipedetail.RecipeDetailActivity;
import com.laaptu.baking.ui.recipeslist.SpacingDecorator.Arrangement;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

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
            recipes = Parcels.unwrap(intent.getParcelableExtra(RECIPES));
        }
        return recipes;
    }

    @BindView(R.id.rv_recipes_list)
    RecyclerView rvRecipesList;

    @Inject
    ImageLoader imageLoader;


    @Override
    public int getLayoutId() {
        return R.layout.activity_recipes_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Recipe> recipes = getRecipesFromIntent(getIntent());
        if (!isRecipesValid(recipes)) {
            showToast(getString(R.string.error_displaying_recipes));
            finish();
            return;
        }
        initViews();
        populateViewWithRecipes(recipes);
    }

    private void initViews() {
        rvRecipesList.setHasFixedSize(true);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int spacing = (int) getResources().getDimension(R.dimen.item_space);
        if (isTablet) {
            rvRecipesList.setLayoutManager(
                new GridLayoutManager(this, 3));
            rvRecipesList.addItemDecoration(new SpacingDecorator(spacing, Arrangement.GRID));
        } else {
            rvRecipesList.setLayoutManager
                (new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rvRecipesList.addItemDecoration(new SpacingDecorator(spacing, Arrangement.VERTICAL));
        }
    }

    private void populateViewWithRecipes(List<Recipe> recipes) {
        RecipesListAdapter recipesListAdapter = new RecipesListAdapter(recipes, imageLoader, eventBus);
        rvRecipesList.setAdapter(recipesListAdapter);
    }

    @Subscribe
    public void onRecipeClicked(Recipe recipe) {
        startActivity(RecipeDetailActivity.getLaunchingIntent(this, recipe));
    }

}
