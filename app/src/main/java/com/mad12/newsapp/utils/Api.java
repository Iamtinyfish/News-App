package com.mad12.newsapp.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.model.Category;

import java.util.List;

public interface Api {
    public static final String BASE_URL = "https://mad-android-thanhpc.herokuapp.com/";
//    public static final String BASE_URL = "http://10.0.2.2:3000/"; //localhost
    @GET("category/")
    Call<List<Category>> getAllCategory();

    @GET("article/id/{id}")
    Call<Article> getArticleById(@Path("id") String id);
}
