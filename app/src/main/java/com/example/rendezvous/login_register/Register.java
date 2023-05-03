package com.example.rendezvous.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
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
import com.example.rendezvous.model.Mode;
import com.example.rendezvous.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    TextView login_text;
    TextUtils textUtils;
    EditText emailEditText, userNameEditText, passwordEditText, confirmEditText;
    CheckBox showPassword;
    Button signUpButton;
    RetrofitService retrofitService = new RetrofitService();
    UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login_text = findViewById(R.id.login_text);
        emailEditText = findViewById(R.id.emailEditText);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmEditText = findViewById(R.id.confirmEditText);
//        emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.required_icon, 0);
//        userNameEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.required_icon, 0);
//        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.required_icon, 0);
//        confirmEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.required_icon, 0);
        showPassword = findViewById(R.id.showPassword);
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (emailEditText.getText().toString().trim().isEmpty() || passwordEditText.getText().toString().trim().isEmpty()
                || userNameEditText.getText().toString().trim().isEmpty() || confirmEditText.getText().toString().trim().isEmpty()) {
                    if (emailEditText.getText().toString().trim().isEmpty()) {
                        emailEditText.setError("Email is required");
                    } else {
                        emailEditText.setError(null);
                    }
                    if (userNameEditText.getText().toString().trim().isEmpty()) {
                        userNameEditText.setError("Username is required");
                    } else {
                        userNameEditText.setError(null);
                    }
                    if (passwordEditText.getText().toString().trim().isEmpty()) {
                        passwordEditText.setError("Password is required");
                    } else {
                        passwordEditText.setError(null);
                    }
                    if (confirmEditText.getText().toString().trim().isEmpty()) {
                        confirmEditText.setError("Please re-enter your password");
                    } else {
                        confirmEditText.setError(null);
                    }

                } else {
                    // Enable the button and clear the error messages
                    signUpButton.setEnabled(true);
                    emailEditText.setError(null);
                    passwordEditText.setError(null);
                    userNameEditText.setError(null);
                    confirmEditText.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        };
        emailEditText.addTextChangedListener(textWatcher);
        userNameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
        confirmEditText.addTextChangedListener(textWatcher);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        signUpButton.setOnClickListener(view ->{
            System.out.println(emailEditText.getText().toString());
            if(!isValidEmail(emailEditText.getText().toString()))
            {
                emailEditText.setError("Invalid Email!");
                return;
            }

            User user = new User();
            user.setEmail(emailEditText.getText().toString());
            user.setUser_name(userNameEditText.getText().toString());
            user.setUser_password(passwordEditText.getText().toString());
            user.setMode(Mode.LEADER);

            userApi.saveUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(Register.this, "Successfully created your account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, Login.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Register.this, "Fail to sign up the account", Toast.LENGTH_SHORT).show();
                }
            });

        });

        login_text.setOnClickListener(view ->
        {
            Intent intent = new Intent(Register.this, Login.class);
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