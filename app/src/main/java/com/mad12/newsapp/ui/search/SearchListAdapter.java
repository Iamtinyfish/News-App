package com.mad12.newsapp.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.ActivityArticleContentBinding;
import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.model.Category;
import com.mad12.newsapp.utils.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchListAdapter extends BaseAdapter {

    private Context context;
    private int resAnInt;
    private List<Article> list;
    private PrettyTime p = new PrettyTime();

    public SearchListAdapter(Context context, int resAnInt, List<Article> list) {
        this.context = context;
        this.resAnInt = resAnInt;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resAnInt,parent,false);
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
