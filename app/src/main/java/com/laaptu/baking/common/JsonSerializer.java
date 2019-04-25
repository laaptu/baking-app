package com.laaptu.baking.common;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import javax.inject.Inject;

public class JsonSerializer {
    private Gson gson;

    @Inject
    public JsonSerializer(Gson gson) {
        this.gson = gson;
    }

    public String toJson(Object item) {
        return gson.toJson(item);
    }

    public <T> T fromJson(String json, Type returnType) {
        return gson.fromJson(json, returnType);
    }
}
