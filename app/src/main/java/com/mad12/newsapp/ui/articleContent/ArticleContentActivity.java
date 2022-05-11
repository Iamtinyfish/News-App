package com.mad12.newsapp.ui.articleContent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.ActivityArticleContentBinding;
import com.mad12.newsapp.model.Article;
import com.mad12.newsapp.ui.login.LoginActivity;
import com.mad12.newsapp.utils.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleContentActivity extends AppCompatActivity {

    ActivityArticleContentBinding binding;
    private TextView articleTitle, articleContent, title_toolbar, article_category, article_date;
    EditText comment;
    private ImageView articleImg, share, img_report, archive;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    private boolean Login, check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityArticleContentBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_article_content);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ArticleContentActivity.this);

        //hide keyboard when click on Screen
        findViewById(R.id.scrollView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                findViewById(R.id.sendComment).setVisibility(View.GONE);
                findViewById(R.id.layout1).setVisibility(View.VISIBLE);
                findViewById(R.id.layout2).setVisibility(View.VISIBLE);
                hideKeyboard(view);
                return false;
            }
        });

        //handle comment
        comment = findViewById(R.id.comment);
        comment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                findViewById(R.id.sendComment).setVisibility(View.VISIBLE);
                findViewById(R.id.layout1).setVisibility(View.GONE);
                findViewById(R.id.layout2).setVisibility(View.GONE);

                findViewById(R.id.sendComment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Login = sharedPref.getBoolean("Login", false);
                        if(!Login){
                            Intent intent = new Intent(ArticleContentActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            System.out.println(comment.getText());
                            comment.getText().clear();
                        }
                    }
                });
                return false;
            }
        });

        //back
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void showToastDown(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //hide keyboard when touch screen
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                article_date = findViewById(R.id.article_date);

                // set value for article
                title_toolbar.setText(response.body().getCategory());
                articleTitle.setText(response.body().getTitle());
                articleContent.setText(response.body().getContent());
                Picasso.get().load(response.body().getImg()).into(articleImg);
                article_category.setText(response.body().getCategory());
                article_date.setText(sdf.format(response.body().getTimestamps()));

                //share
                Article article = response.body();
                share = findViewById(R.id.share);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT, article.getTitle());
                        share.putExtra(Intent.EXTRA_TEXT, article.getImg());
                        share.putExtra(Intent.EXTRA_TEXT, article.getContent());
                        startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
                    }
                });

                //handle archive
                archive = findViewById(R.id.archive);
                archive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            FileOutputStream fOut = openFileOutput("archive.txt", MODE_APPEND);
                            PrintWriter writer = new PrintWriter( new OutputStreamWriter( fOut ) );

                            FileInputStream fis = openFileInput("archive.txt");
                            BufferedReader reader = new BufferedReader( new InputStreamReader( fis ) );
                            String line;
                            //check duplicate write file
                            while((line = reader.readLine()) != null){
                                if(line.equals(article.getId())){
                                    check = true;
                                    Toast.makeText(ArticleContentActivity.this,"Tin này đã được lưu", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                            if(!check){
                                writer.println(article.getId());
                                check = false;
                                Toast.makeText(ArticleContentActivity.this,"Đã lưu tin", Toast.LENGTH_LONG).show();
                            }

                            writer.close();
                            reader.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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