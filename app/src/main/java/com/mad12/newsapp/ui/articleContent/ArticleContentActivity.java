package com.mad12.newsapp.ui.articleContent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.ActivityArticleContentBinding;
import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.utils.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleContentActivity extends AppCompatActivity {

    ActivityArticleContentBinding binding;
    private TextView articleTitle, articleContent;
    private ImageView articleImg;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityArticleContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //get article id from intent
        Intent intent = this.getIntent();
        String articleId = null;
        if (intent != null) articleId = intent.getStringExtra("article_id");
        getArticleById(articleId);

//        Log.println(Log.DEBUG,"articleId", (articleId != null) ? articleId : "null");

    }

    //back button action
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    //

    //Call API
    private void getArticleById(String id) {
        Call<Article> call = RetrofitClient.getInstance().getMyApi().getArticleById(id);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                // define view
                articleTitle = findViewById(R.id.article_title);
                articleContent = findViewById(R.id.article_content);
                articleImg = findViewById(R.id.article_img);

                // set value for article
                articleTitle.setText(response.body().getTitle());
                articleContent.setText(response.body().getContent());
                Picasso.get().load(response.body().getImg()).into(articleImg);
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.println(Log.DEBUG,"error call api", t.toString());
            }
        });
    }
}