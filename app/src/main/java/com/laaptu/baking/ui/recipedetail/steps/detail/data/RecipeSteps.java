package com.laaptu.baking.ui.recipedetail.steps.detail.data;

import com.laaptu.baking.data.models.Step;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class RecipeSteps {
    public String recipeName = "";
    public List<Step> steps = new ArrayList<>();
    public int selectedStep = 0;

    public RecipeSteps(String recipeName, List<Step> steps, int selectedStep) {
        this.recipeName = recipeName;
        this.steps = steps;
        this.selectedStep =selectedStep;
    }

    public RecipeSteps() {

    }
}
