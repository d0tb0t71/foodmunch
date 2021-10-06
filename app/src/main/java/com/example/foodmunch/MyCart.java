package com.example.foodmunch;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {

    RecyclerView cart_recycler_view;
    CartAdapter cartAdapter;
    ArrayList<CartModel> list;
    Button place_order_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        getSupportActionBar().setTitle("My Cart");


        cart_recycler_view=findViewById(R.id.cart_recycler_view);

        cart_recycler_view.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(MyCart.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        cart_recycler_view.setLayoutManager(layoutManager);


        list = new ArrayList<>();
        cartAdapter = new CartAdapter(this, list);
        cart_recycler_view.setAdapter(cartAdapter);






        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String my_uid = user.getUid();

        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("My Cart").child(my_uid);

        databaseReference0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    CartModel cartModel = dataSnapshot.getValue(CartModel.class);

                        list.add(cartModel);


                }

                cartAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }
}