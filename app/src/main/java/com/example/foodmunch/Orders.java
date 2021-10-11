package com.example.foodmunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {

    RecyclerView orders_recyclerview;
    String my_uid;
    OrderAdapter orderAdapter;
    ArrayList<ModelOrder> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);



        getSupportActionBar().setTitle("Orders");




        orders_recyclerview = findViewById(R.id.orders_recyclerview);


        orders_recyclerview.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(Orders.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        orders_recyclerview.setLayoutManager(layoutManager);


        list = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, list);
        orders_recyclerview.setAdapter(orderAdapter);






        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        my_uid= user.getUid();

       FirebaseDatabase database=FirebaseDatabase.getInstance();


       DatabaseReference reference=database.getReference("Shop Orders").child(my_uid);

        reference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ModelOrder modelOrder = dataSnapshot.getValue(ModelOrder.class);

                    list.add(modelOrder);


                }

                orderAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}