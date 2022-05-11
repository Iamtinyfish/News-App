package com.mad12.newsapp.ui.home;

import android.util.Log;

import com.mad12.newsapp.model.Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mad12.newsapp.utils.RetrofitClient;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Category>> categoriesData;

    public HomeViewModel() {
        categoriesData = new MutableLiveData<>();
        getCategoriesFromAPI();
    }

    public LiveData<List<Category>> getCategoriesLiveData() {
        return categoriesData;
    }

    public void setCategoriesData(List<Category> categories) {
        this.categoriesData.setValue(categories);
    }

    //Call API
    private void getCategoriesFromAPI() {
        Call<List<Category>> call = RetrofitClient.getInstance().getMyApi().getAllCategory();
//        Log.println(Log.DEBUG,"url", call.request().url().toString());
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categoriesData.setValue(response.body());
//                Log.println(Log.DEBUG,"categoriesData", (categoriesData != null) ? categoriesData.getValue().toString() : "null");
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.println(Log.DEBUG,"error call api", t.toString());
            }
        });
    }
}