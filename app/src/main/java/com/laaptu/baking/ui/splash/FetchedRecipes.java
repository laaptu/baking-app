package com.laaptu.baking.ui.splash;

import com.laaptu.baking.data.models.Recipe;

import java.util.List;

public class FetchedRecipes {
    public boolean isCachedRecipes;
    public List<Recipe> recipes;

    public FetchedRecipes(boolean isCachedRecipes, List<Recipe> recipes) {
        this.isCachedRecipes = isCachedRecipes;
        this.recipes = recipes;
    }
}
