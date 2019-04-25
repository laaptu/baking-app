package com.laaptu.baking.data.api;

import com.laaptu.baking.data.models.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RecipesApiService {
    @GET(ApiConstants.PATH_RECIPES)
    Single<List<Recipe>> getAllRecipes();
}
