package com.mad12.newsapp.ui.articleList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.FragmentArticleListBinding;
import com.mad12.newsapp.model.ArticleId;

import java.util.List;

public class ArticleListFragment extends Fragment {

    private FragmentArticleListBinding binding;
    private List<ArticleId> articles;

    public ArticleListFragment(List<ArticleId> articles) {
        this.articles = articles;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentArticleListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView articleList = binding.articleList;

        ArticleListAdapter adapter = new ArticleListAdapter(articles,getContext());
        articleList.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}