package com.example.foodmunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button login_now,register_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login_now=findViewById(R.id.login_now);
        register_now=findViewById(R.id.register_now);

        login_now.setOnClickListener(v ->{

                startActivity(new Intent(getApplicationContext(),LoginNow.class));

        });

        login_now.setOnClickListener(v ->{

            startActivity(new Intent(getApplicationContext(),RegisterNow.class));

        });

    }
}