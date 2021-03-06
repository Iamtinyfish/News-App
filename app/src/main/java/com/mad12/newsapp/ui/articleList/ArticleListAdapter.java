package com.mad12.newsapp.ui.articleList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mad12.newsapp.R;
import com.mad12.newsapp.model.Article;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;
import java.util.List;

public class ArticleListAdapter extends ArrayAdapter<Article> {
    private PrettyTime p = new PrettyTime();

    public ArticleListAdapter(@NonNull Context context, @NonNull List<Article> articles) {
        super(context, R.layout.article_item, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_item,parent,false);
        }

        ImageView articleImg = convertView.findViewById(R.id.article_img);
        TextView articleTitle = convertView.findViewById(R.id.article_title);
        TextView articleCategory = convertView.findViewById(R.id.article_category);
        TextView article_date = convertView.findViewById((R.id.article_date));

        if(article.getImg() != null && !article.getImg().isEmpty()){
            Picasso.get().load(article.getImg()).into(articleImg);
        }
        articleTitle.setText(article.getTitle());
        articleCategory.setText(article.getCategory());
        article_date.setText(p.format(article.getTimestamps()));

        return convertView;
    }
}
