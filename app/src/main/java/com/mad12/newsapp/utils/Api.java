package com.mad12.newsapp.utils;

import retrofit2.Call;
import retrofit2.http.GET;

import com.mad12.newsapp.model.Categories;

public interface Api {
    public static final String BASE_URL = "https://mad-android-thanhpc.herokuapp.com/";
    @GET("category/")
    Call<Categories> getAllCategory();
}
