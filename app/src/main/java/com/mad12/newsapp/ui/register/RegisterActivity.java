package com.mad12.newsapp.ui.register;

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
import com.mad12.newsapp.databinding.ActivityRegisterBinding;
import com.mad12.newsapp.model.User;
import com.mad12.newsapp.ui.articleContent.ArticleContentActivity;
import com.mad12.newsapp.ui.login.LoginActivity;
import com.mad12.newsapp.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    EditText username, password;
    View register;
    User user = new User();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        register = findViewById(R.id.register);
        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User(username.getText().toString().trim(), password.getText().toString().trim());
                register(user);
            }
        });

    }

    //Call API
    private void register(User user) {
        Call<String> call = RetrofitClient.getInstance().getMyApi().register(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() != null){
                    Toast.makeText(RegisterActivity.this,"Register Success!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Username or password invalid", Toast.LENGTH_LONG).show();
            }
        });
    }
}
