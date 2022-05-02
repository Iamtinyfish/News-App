package com.mad12.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {
    @SerializedName("article")
    private List<ArticleId> articleId;

    public List<ArticleId> getArticleId() {
        return articleId;
    }

    public void setArticleId(List<ArticleId> articleId) {
        this.articleId = articleId;
    }
}
