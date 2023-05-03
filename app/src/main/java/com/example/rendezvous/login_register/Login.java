package com.example.rendezvous.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rendezvous.R;
import com.example.rendezvous.RetrofitService;
import com.example.rendezvous.api.UserApi;
import com.example.rendezvous.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView sign_up_text;
    EditText loginEmailEditText, loginPasswordText;
    Button loginButton;
    CheckBox loginShowPassword;
    RetrofitService retrofitService = new RetrofitService();
    UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign_up_text = findViewById(R.id.sign_up_text);
        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordText = findViewById(R.id.loginPasswordText);
        loginButton = findViewById(R.id.loginButton);
        loginShowPassword = findViewById(R.id.loginShowPassword);
        loginShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    loginPasswordText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    loginPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        loginButton.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginEmailEditText.getText().toString().trim().isEmpty() || loginPasswordText.getText().toString().trim().isEmpty()) {
                    // Disable the button or show an error message
                    if (loginEmailEditText.getText().toString().trim().isEmpty()) {
                        loginEmailEditText.setError("Email is required");
                    } else {
                        loginEmailEditText.setError(null);
                    }
                    if (loginPasswordText.getText().toString().trim().isEmpty()) {
                        loginPasswordText.setError("Password is required");
                    } else {
                        loginPasswordText.setError(null);
                    }
                } else {
                    // Enable the button and clear the error messages
                    loginButton.setEnabled(true);
                    loginEmailEditText.setError(null);
                    loginPasswordText.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        };

        loginEmailEditText.addTextChangedListener(textWatcher);
        loginPasswordText.addTextChangedListener(textWatcher);

        loginButton.setOnClickListener(view -> {
            String inputEmail = loginEmailEditText.getText().toString();
            String inputPassword = loginPasswordText.getText().toString();
            if(!isValidEmail(inputEmail))
            {
                loginEmailEditText.setError("Invalid Email");
                return;
            }
            userApi.authenticateUser(new User(inputEmail, inputPassword)).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        System.out.println(user);
                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        int statusCode = response.code();
                        if (statusCode == 400) {
                            Toast.makeText(Login.this, "Incorrect Passwords", Toast.LENGTH_SHORT).show();
                        } else if (statusCode == 404) {
                            Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Login Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Login.this, "Connection Error!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        sign_up_text.setOnClickListener(view ->
        {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}