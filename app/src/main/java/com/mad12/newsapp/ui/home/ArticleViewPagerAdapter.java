package com.mad12.newsapp.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.model.Category;
import com.mad12.newsapp.ui.articleList.ArticleListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArticleViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Category> categoriesData;
    private List<Article> allArticles;
    Date date = null;
    public ArticleViewPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Category> categoriesData) {
        super(fm, behavior);
        this.categoriesData = categoriesData;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        List<Article> articles = new ArrayList<>();
        if (position == 0) {
            if (allArticles == null) {
                for (Category category : categoriesData) {
                    for (Article article : category.getArticles()) {
                        article.setCategory(category.getName());
                        articles.add(article);
                    }
                }
                allArticles = articles;
            } else {
                articles = allArticles;
            }
        } else {
            articles = categoriesData.get(position - 1).getArticles();
        }

        return new ArticleListFragment(articles);
    }

    @Override
    public int getCount() {
        return categoriesData.size()+1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "Tất cả";
        if (position > 0) title = categoriesData.get(position - 1).getName();
        return title;
    }
}
