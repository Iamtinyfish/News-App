package com.mad12.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("data")
    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
