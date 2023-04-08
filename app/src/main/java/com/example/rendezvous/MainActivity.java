package com.example.rendezvous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button goToButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToButton = findViewById(R.id.goToButton);
        goToButton.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });
    }
}