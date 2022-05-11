package com.mad12.newsapp.utils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.model.Category;
import com.mad12.newsapp.model.User;

import java.util.Arrays;
import java.util.List;

public interface Api {
//    public static final String BASE_URL = "https://mad-android-thanhpc.herokuapp.com/";
    public static final String BASE_URL = "http://10.0.2.2:5000/"; //localhost

    @GET("category/")
    Call<List<Category>> getAllCategory();

    @GET("article/id/{id}")
    Call<Article> getArticleById(@Path("id") String id);

    @GET("article/search")
    Call<List<Article>> getArticlesByKey(@Query("title") String key);

    @POST("article/saved")
    Call<List<Article>> getArticlesSaved(@Body List<String> arrays);

    @POST("login")
    Call<User> checkLogin(@Body User user);
}