package com.mad12.newsapp.ui.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad12.newsapp.R;
import com.mad12.newsapp.model.Article;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

public class BookmarkListAdapter extends ArrayAdapter<Article> {

    private Context context;
    private int resAnInt;
    private List<Article> list;
    private PrettyTime p = new PrettyTime();

    public BookmarkListAdapter(Context context, List<Article> list) {
        super(context, R.layout.article_item, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_item,viewGroup,false);
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
