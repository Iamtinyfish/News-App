package me.tinyfish.newsapp.model;

import java.util.List;

public class Category {
    private String name;
    private String slug;
    private List<Article> articles;

    public Category(String name, String slug, List<Article> articles) {
        this.name = name;
        this.slug = slug;
        this.articles = articles;
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

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
