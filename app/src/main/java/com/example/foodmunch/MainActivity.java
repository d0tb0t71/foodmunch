package com.example.foodmunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        register_now.setOnClickListener(v ->{

            startActivity(new Intent(getApplicationContext(),UserOrSeller.class));

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startActivity(new Intent(getApplicationContext(),HomePage.class));

        }

    }

}