package com.laaptu.baking.data.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipe {
    public String image = "";
    public int servings = 0;
    public String name = "";
    public List<Ingredient> ingredients = new ArrayList<>();
    public int id = 0;
    public List<Step> steps = new ArrayList<>();
}

