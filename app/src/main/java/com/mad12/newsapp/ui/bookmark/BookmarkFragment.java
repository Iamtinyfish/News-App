package com.mad12.newsapp.ui.bookmark;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.mad12.newsapp.databinding.FragmentBookmarkBinding;
import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.model.Category;
import com.mad12.newsapp.ui.home.ArticleViewPagerAdapter;
import com.mad12.newsapp.utils.RetrofitClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkFragment extends Fragment {

    private FragmentBookmarkBinding binding;
    List<String> arraysId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            arraysId = new ArrayList<>();
            FileInputStream fis = getContext().openFileInput("archive.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while ((reader.readLine()) != null){
                arraysId.add(reader.readLine());
                System.out.println(reader.readLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        getArticlesSaved(arraysId);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Call API
    private void getArticlesSaved(List<String> arrays) {
        Call<List<Article>> call = RetrofitClient.getInstance().getMyApi().getArticlesSaved(arrays);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
//                articles.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.println(Log.DEBUG,"error call api", t.toString());
            }
        });
    }

}