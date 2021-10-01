package com.example.foodmunch;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyShop extends AppCompatActivity {

    FloatingActionButton add_item_floating_btn;

    RecyclerView my_shop_recyclerview;
    AdapterItem adapterItem;
    ArrayList<ModelItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);

        getSupportActionBar().setTitle("My Shop");

        add_item_floating_btn = findViewById(R.id.add_item_floating_btn);

        my_shop_recyclerview = findViewById(R.id.my_shop_recyclerview);


        my_shop_recyclerview.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(MyShop.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        my_shop_recyclerview.setLayoutManager(layoutManager);


        list = new ArrayList<>();
        adapterItem = new AdapterItem(this, list);
        my_shop_recyclerview.setAdapter(adapterItem);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String my_uid = user.getUid();

        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("Item List").child(my_uid);

        databaseReference0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ModelItem modelItem = dataSnapshot.getValue(ModelItem.class);

                    list.add(modelItem);


                }

                adapterItem.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





















        add_item_floating_btn.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), AddItem.class));

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomePage.class));

    }
}