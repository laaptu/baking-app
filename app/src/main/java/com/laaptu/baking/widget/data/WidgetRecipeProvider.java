package com.laaptu.baking.widget.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.laaptu.baking.common.JsonSerializer;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.di.AppModule;

import javax.inject.Inject;

public class WidgetRecipeProvider {
    private static final String RECIPE_PREF_NAME = "WidgetRecipe";
    private static final String RECIPE = "recipe";

    private SharedPreferences widgetRecipeSharedPref;
    private JsonSerializer jsonSerializer;

    @Inject
    public WidgetRecipeProvider() {
        widgetRecipeSharedPref = AppModule.getAppContext().getSharedPreferences(RECIPE_PREF_NAME,
                Context.MODE_PRIVATE);
        jsonSerializer = new JsonSerializer(AppModule.getGson());
    }

    public void saveRecipe(Recipe recipe) {
        String recipesAsJson = jsonSerializer.toJson(recipe);
        Editor editor = widgetRecipeSharedPref.edit();
        editor.putString(RECIPE, recipesAsJson);
        editor.apply();
    }

    public Recipe getRecipe() {
        Recipe recipe = null;
        if (widgetRecipeSharedPref.contains(RECIPE)) {
            String recipeAsJson = widgetRecipeSharedPref.getString(RECIPE, null);
            if (recipeAsJson != null) {
                recipe = jsonSerializer.fromJson(recipeAsJson, Recipe.class);
            }
        }
        return recipe;
    }
}
