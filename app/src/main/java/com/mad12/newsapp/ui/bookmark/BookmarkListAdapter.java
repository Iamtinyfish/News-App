package com.mad12.newsapp.ui.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad12.newsapp.R;
import com.mad12.newsapp.model.Article;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

public class BookmarkListAdapter extends BaseAdapter {

    private Context context;
    private int resAnInt;
    private List<Article> list;
    private PrettyTime p = new PrettyTime();

    public BookmarkListAdapter(Context context, int resAnInt, List<Article> list) {
        this.context = context;
        this.resAnInt = resAnInt;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(context).inflate(resAnInt, viewGroup,false);
        ImageView articleImg = convertView.findViewById(R.id.article_img);
        TextView articleTitle = convertView.findViewById(R.id.article_title);
        TextView articleCategory = convertView.findViewById(R.id.article_category);
        TextView articleDate = convertView.findViewById(R.id.article_date);

        Picasso.get().load(list.get(position).getImg()).into(articleImg);
        articleTitle.setText(list.get(position).getTitle());
        articleCategory.setText(list.get(position).getCategory());
        articleDate.setText(p.format(list.get(position).getTimestamps()));

        return convertView;
    }
}
