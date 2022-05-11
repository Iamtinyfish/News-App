package com.mad12.newsapp.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.mad12.newsapp.R;
import com.mad12.newsapp.databinding.ActivityLoginBinding;
import com.mad12.newsapp.model.User;
import com.mad12.newsapp.ui.articleContent.ArticleContentActivity;
import com.mad12.newsapp.ui.register.RegisterActivity;
import com.mad12.newsapp.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    private ActivityLoginBinding binding;
    EditText username, password;
    View login, inflatedView, btnRegister;
    User user = new User();
    private Button logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnRegister = findViewById(R.id.btnRegister);
        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        //handle login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User(username.getText().toString().trim(), password.getText().toString().trim());
                checkLogin(user);
            }
        });

        //registerBtn click
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //Call API
    private void checkLogin(User user) {
        Call<User> call = RetrofitClient.getInstance().getMyApi().checkLogin(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null){
                    user.setFullname(response.body().getFullname());
                    user.setId(response.body().getId());
                    user.setUsername(response.body().getUsername());
                    user.setPassword(response.body().getPassword());

                    final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("Login", true);
                    editor.putString("Username", user.getUsername());
                    editor.putString("Password", user.getPassword());
                    editor.putString("Fullname", user.getFullname());
                    editor.apply();
                    finish();
                    ArticleContentActivity articleContentActivity = new ArticleContentActivity();
                    articleContentActivity.showToastDown(LoginActivity.this, "Login Success!");

                    //set text logout
                    inflatedView = getLayoutInflater().inflate(R.layout.nav_header_main, null);
                    logout = inflatedView.findViewById(R.id.btnLogout);
                    logout.setText("Đăng xuất");
                }
                else{
                    Toast.makeText(LoginActivity.this,"Username or password invalid", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Username or password invalid", Toast.LENGTH_LONG).show();
            }
        });
    }
}