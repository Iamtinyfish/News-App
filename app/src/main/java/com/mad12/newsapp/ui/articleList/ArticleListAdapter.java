package com.mad12.newsapp.ui.articleList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad12.newsapp.R;
import com.mad12.newsapp.model.ArticleId;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleListAdapter extends BaseAdapter {
    private List<ArticleId> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ArticleListAdapter(List<ArticleId> listData, Context context) {
        this.listData = listData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.article_item, null);
            holder = new ViewHolder();
            holder.itemImg = (ImageView) view.findViewById(R.id.itemImg);
            holder.textTitle = (TextView) view.findViewById(R.id.textTitle);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ArticleId article = this.listData.get(i);
        holder.textTitle.setText(article.getTitle());
        Picasso.get().load(article.getImg()).into(holder.itemImg);

        return view;
    }

    static class ViewHolder {
        ImageView itemImg;
        TextView textTitle;
    }
}
