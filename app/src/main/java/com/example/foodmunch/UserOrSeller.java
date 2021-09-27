package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserOrSeller extends AppCompatActivity {

    Button regi_customer,regi_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_or_seller);


        regi_customer = findViewById(R.id.as_customer);
        regi_admin=findViewById(R.id.as_admin);


        regi_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), RegisterNow.class);
                intent.putExtra("userStatus","admin");
                startActivity(intent);

            }
        });

        regi_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), RegisterNow.class);
                intent.putExtra("userStatus","customer");
                startActivity(intent);

            }
        });
    }
}