package com.example.foodmunch;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterNow extends AppCompatActivity {

    Button register_now,go_login_now;
    EditText name_ET,email_ET,mobile_ET,address_ET,password_ET,confirm_password_ET;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_now);

        String userStatus= getIntent().getStringExtra("userStatus");


        register_now=findViewById(R.id.register_now);
        go_login_now=findViewById(R.id.go_login_now);


        name_ET=findViewById(R.id.name_ET);
        email_ET=findViewById(R.id.email_ET);
        mobile_ET=findViewById(R.id.mobile_ET);
        address_ET=findViewById(R.id.address_ET);
        password_ET=findViewById(R.id.password_ET);
        confirm_password_ET=findViewById(R.id.confirm_password_ET);


        mAuth=FirebaseAuth.getInstance();


        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=name_ET.getText().toString();
                String email=email_ET.getText().toString();
                String mobile=mobile_ET.getText().toString();
                String address=address_ET.getText().toString();
                String pass= password_ET.getText().toString();
                String con_pass=confirm_password_ET.getText().toString();


                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            FirebaseUser user=mAuth.getCurrentUser();

                            String uid= user.getUid();

                            HashMap<Object ,String> hashMap = new HashMap<>();

                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name",name);
                            hashMap.put("mobile",mobile);
                            hashMap.put("address",address);
                            hashMap.put("userStatus",userStatus);


                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            DatabaseReference reference=database.getReference("Users");
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), HomePage.class ));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });

        go_login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginNow.class));
            }
        });



    }
}