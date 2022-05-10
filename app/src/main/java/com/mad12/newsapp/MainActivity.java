package com.mad12.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.mad12.newsapp.databinding.ActivityMainBinding;
import com.mad12.newsapp.ui.articleContent.ArticleContentActivity;
import com.mad12.newsapp.ui.login.LoginActivity;
import com.mad12.newsapp.ui.notification.NotificationActivity;
import com.mad12.newsapp.ui.search.SearchActivity;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private SearchView searchView;
    private TextView textView, textUsername;
    private String username = null;
    private Button logout;
    private boolean Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_bookmark, R.id.nav_about, R.id.nav_personal)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        textView = findViewById(R.id.searchBtn);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(MainActivity.this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = searchView.getQuery().toString();
                if(key.trim().length() != 0){
                    Intent i = new Intent(MainActivity.this, SearchActivity.class);
                    i.putExtra("key", key);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_notification:
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        logout = findViewById(R.id.btnLogout);
        textUsername = findViewById(R.id.txtusername);
        //set username text
        username = sharedPref.getString("Fullname", "");
        if(!username.isEmpty()) {
            textUsername.setText(username);
            logout.setText("Đăng xuất");
        }else{
            textUsername.setText("News App");
        }

        //set logout button
        Login = sharedPref.getBoolean("Login", false);
        if(!Login){
            logout.setText("Đăng nhập");
        }
        else{
            logout.setText("Đăng xuất");
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor= sharedPref.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(MainActivity.this,"Logout Success", Toast.LENGTH_LONG);
                    textUsername.setText("News App");
                    logout.setText("Đăng nhập");
                }
            });
        }

        if(logout.getText().equals("Đăng nhập")){
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

}