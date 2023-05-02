package com.example.rendezvous.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rendezvous.R;

public class Login extends AppCompatActivity {
    TextView sign_up_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign_up_text = findViewById(R.id.sign_up_text);
        sign_up_text.setOnClickListener(view ->
        {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
}