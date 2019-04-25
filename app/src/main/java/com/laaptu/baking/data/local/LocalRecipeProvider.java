package com.laaptu.baking.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.reflect.TypeToken;
import com.laaptu.baking.common.JsonSerializer;
import com.laaptu.baking.data.models.Recipe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

public class LocalRecipeProvider {

    private static final String RECIPE_PREF_NAME = "LocalRecipe";
    private static final String RECIPES = "recipes";

    private SharedPreferences localRecipeSharedPref;
    private JsonSerializer jsonSerializer;

    @Inject
    public LocalRecipeProvider(Context context, JsonSerializer jsonSerializer) {
        localRecipeSharedPref = context.getSharedPreferences(RECIPE_PREF_NAME, Context.MODE_PRIVATE);
        this.jsonSerializer = jsonSerializer;
    }

    public Single<List<Recipe>> getAllRecipes() {
        return Single.fromCallable(this::getAllLocalRecipes);
    }

    private List<Recipe> getAllLocalRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        if (localRecipeSharedPref.contains(RECIPES)) {
            String storedRecipes = localRecipeSharedPref.getString(RECIPES, null);
            if (storedRecipes != null) {
                Type recipeListType = new TypeToken<ArrayList<Recipe>>() {
                }.getType();
                recipes = jsonSerializer.fromJson(storedRecipes, recipeListType);
            }
        }
        return recipes;
    }

    public Single<Boolean> storeAllRecipes(List<Recipe> recipes) {
        return Single.fromCallable(() -> storeRecipes((recipes)));
    }

    private Boolean storeRecipes(List<Recipe> recipes) {
        if (recipes != null && recipes.size() > 0) {
            Timber.d("Storing the fetched recipes of size = %d", recipes.size());
            String recipesAsJson = jsonSerializer.toJson(recipes);
            Editor editor = localRecipeSharedPref.edit();
            editor.putString(RECIPES, recipesAsJson);
            editor.apply();
            return true;
        }
        return true;
    }
}
