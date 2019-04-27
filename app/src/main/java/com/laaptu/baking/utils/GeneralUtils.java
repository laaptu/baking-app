package com.laaptu.baking.utils;

import android.webkit.URLUtil;

import com.laaptu.baking.data.models.Recipe;

import java.util.List;

public class GeneralUtils {
    public static boolean isRecipesValid(List<Recipe> recipes) {
        return recipes != null && recipes.size() > 0;
    }

    public static boolean isValidUrl(String url) {
        return URLUtil.isValidUrl(url);
    }
}
