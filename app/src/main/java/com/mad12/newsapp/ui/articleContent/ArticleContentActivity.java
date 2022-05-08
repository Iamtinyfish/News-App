package com.mad12.newsapp.ui.articleContent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
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
    private TextView articleTitle, articleContent, title_toolbar,article_category;
    private ImageView articleImg, share, img_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityArticleContentBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_article_content);

        //back
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ArticleContentActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        //get article id from intent
        Intent intent = this.getIntent();
        String articleId = null;
        if (intent != null) articleId = intent.getStringExtra("article_id");
        getArticleById(articleId);

        //add menu report
        img_report = findViewById(R.id.img_report);
        img_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pm = new PopupMenu(ArticleContentActivity.this, img_report);
                pm.getMenuInflater().inflate(R.menu.nav_top, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(ArticleContentActivity.this, "", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                pm.setForceShowIcon(true);
                pm.show();
            }
        });
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
                title_toolbar = findViewById(R.id.title_toolbar);
                article_category = findViewById(R.id.article_category);

                // set value for article
                title_toolbar.setText(response.body().getCategory());
                articleTitle.setText(response.body().getTitle());
                articleContent.setText(response.body().getContent());
                Picasso.get().load(response.body().getImg()).into(articleImg);
                article_category.setText(response.body().getCategory());

                //share
                share = findViewById(R.id.share);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Article article = response.body();
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT, article.getTitle());
                        share.putExtra(Intent.EXTRA_TEXT, article.getImg());
                        share.putExtra(Intent.EXTRA_TEXT, article.getContent());
                        startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
                    }
                });
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.println(Log.DEBUG,"error call api", t.toString());
            }
        });
    }

}