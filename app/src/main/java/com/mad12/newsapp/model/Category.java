package com.mad12.newsapp.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("slug")
    private String slug;
    @SerializedName("articles")
    private List<ArticleId> articleIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<ArticleId> getArticles() {
        return articleIds;
    }

    public void setArticles(List<ArticleId> articleIds) {
        this.articleIds = articleIds;
    }
}
