package com.mad12.newsapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mad12.newsapp.databinding.ActivityArticleContentBinding;

public class ArticleContentActivity extends AppCompatActivity {

    ActivityArticleContentBinding binding;

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
}