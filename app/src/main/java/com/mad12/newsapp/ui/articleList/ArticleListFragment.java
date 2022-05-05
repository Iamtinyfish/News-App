package com.mad12.newsapp.ui.articleList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mad12.newsapp.databinding.FragmentArticleListBinding;
import com.mad12.newsapp.ui.articleContent.ArticleContentActivity;
import com.mad12.newsapp.model.Article;

import java.util.List;

public class ArticleListFragment extends Fragment {

    private FragmentArticleListBinding binding;
    private List<Article> articles;

    public ArticleListFragment(List<Article> articles) {

        this.articles = articles;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentArticleListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView articleList = binding.articleList;

//        ArrayAdapter<Article> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, articles);
        ArticleListAdapter adapter = new ArticleListAdapter(getContext(),articles);
        articleList.setAdapter(adapter);

        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
                intent.putExtra("article_id",articles.get(position).getId());
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}