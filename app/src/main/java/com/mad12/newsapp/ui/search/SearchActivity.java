package com.mad12.newsapp.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.mad12.newsapp.MainActivity;
import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.ActivityArticleContentBinding;
import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.model.Category;
import com.mad12.newsapp.ui.articleContent.ArticleContentActivity;
import com.mad12.newsapp.utils.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ActivityArticleContentBinding binding;
    ListView listView;
    private String key = null, slug = null;
    private SearchView searchView;
    private TextView textView, noResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleContentBinding.inflate(getLayoutInflater());
        setContentView(R.layout.search_result);

        //back
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView = findViewById(R.id.listView);
        key = getIntent().getStringExtra("key");
        getArticlesByKey(key);

        textView = findViewById(R.id.searchBtn);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQuery(key,false);
        searchView.setOnQueryTextListener(SearchActivity.this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = searchView.getQuery().toString();
                if(key.trim().length() != 0){
                    getArticlesByKey(key);
                }
            }
        });
    }

    //Call API
    private void getArticlesByKey(String key) {
        Call<List<Article>> call = RetrofitClient.getInstance().getMyApi().getArticlesByKey(key);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.body() != null){
                    //no result
                    noResult = findViewById(R.id.text_no_search);
                    if(response.body().size() == 0){
                        noResult.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        noResult.setText("Không có dữ liệu hiển thị");
                    }
                    else{
                        noResult.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        SearchListAdapter adapter = new SearchListAdapter(SearchActivity.this,R.layout.article_item, response.body());
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                Intent intent = new Intent(SearchActivity.this, ArticleContentActivity.class);
                                intent.putExtra("article_id", response.body().get(position).getId());

                                startActivity(intent);
                            }
                        });
                    }
                }else{
                    Log.v("v","error call api");
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.println(Log.DEBUG,"error call api", t.toString());
            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
