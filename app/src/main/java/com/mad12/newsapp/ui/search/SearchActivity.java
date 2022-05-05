package com.mad12.newsapp.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mad12.newsapp.MainActivity;
import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.ActivityArticleContentBinding;
import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.ui.articleContent.ArticleContentActivity;
import com.mad12.newsapp.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ActivityArticleContentBinding binding;
    ListView listView;
    private String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleContentBinding.inflate(getLayoutInflater());
        setContentView(R.layout.search_result);
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
        getCategoryByKey(key);
    }

    //Call API
    private void getCategoryByKey(String key) {

        Call<List<Article>> call = RetrofitClient.getInstance().getMyApi().getCategoryByKey(key);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {

                if(response.body() != null){
                    SearchListAdapter adapter = new SearchListAdapter(SearchActivity.this,R.layout.article_item, response.body());

                    if(listView != null) {
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
                    Log.v("d","error call api");
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.println(Log.DEBUG,"error call api", t.toString());
            }
        });
    }
}
