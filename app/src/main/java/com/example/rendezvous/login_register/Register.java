package com.example.rendezvous.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rendezvous.R;

public class Register extends AppCompatActivity {
    TextView login_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login_text = findViewById(R.id.login_text);
        login_text.setOnClickListener(view ->
        {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
}