package com.mad12.newsapp.ui.articleContent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mad12.newsapp.MainActivity;
import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.ActivityArticleContentBinding;
import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.ui.search.SearchActivity;
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
        setContentView(R.layout.activity_article_content);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleContentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //get article id from intent
        Intent intent = this.getIntent();
        String articleId = null;
        if (intent != null) articleId = intent.getStringExtra("article_id");
        getArticleById(articleId);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

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