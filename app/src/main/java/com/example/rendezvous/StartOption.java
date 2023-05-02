package com.example.rendezvous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.rendezvous.login_register.Login;
import com.example.rendezvous.login_register.Register;

public class StartOption extends AppCompatActivity {
    Button register_button, login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_option);
        register_button = findViewById(R.id.register_button);
        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(view ->
        {
            Intent intent = new Intent(StartOption.this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        register_button.setOnClickListener(view ->
        {
            Intent intent = new Intent(StartOption.this, Register.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}