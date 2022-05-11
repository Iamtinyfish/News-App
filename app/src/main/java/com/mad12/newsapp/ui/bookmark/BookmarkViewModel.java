package com.mad12.newsapp.ui.bookmark;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.utils.RetrofitClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkViewModel extends ViewModel {

    private MutableLiveData<List<Article>> articles;
    List<String> arraysId;

    public BookmarkViewModel() {
//        try {
//            FileInputStream fis = Context.class.openFileInput("archive.txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//            while ((reader.readLine()) != null){
//                arraysId.add(reader.readLine());
//            }
//            System.out.println(arraysId.size());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        articles = new MutableLiveData<>();
//        getArticlesSaved(arraysId);
    }

    public LiveData<List<Article>> getArticlesLiveData() {
        return articles;
    }

    public void setArticlesData(List<Article> articles) {
        this.articles.setValue(articles);
    }

    //Call API
//    private void getArticlesSaved(List<String> arrays) {
//        Call<List<Article>> call = RetrofitClient.getInstance().getMyApi().getArticlesSaved(arrays);
//        call.enqueue(new Callback<List<Article>>() {
//            @Override
//            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
//                articles.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<Article>> call, Throwable t) {
//                Log.println(Log.DEBUG,"error call api", t.toString());
//            }
//        });
//    }

}