package com.laaptu.baking.data.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Ingredient {
    public float quantity = 0f;
    public String measure = "";
    @SerializedName("ingredient")
    public String name = "";
}
