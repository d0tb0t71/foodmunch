package com.example.foodmunch;

import android.content.Intent;
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
import java.util.HashMap;

public class MyCart extends AppCompatActivity {

    RecyclerView cart_recycler_view;
    CartAdapter cartAdapter;
    ArrayList<CartModel> list;
    Button place_order_btn;

    String BuyerName,BuyerAddress,BuyerMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        getSupportActionBar().setTitle("My Cart");


        cart_recycler_view=findViewById(R.id.cart_recycler_view);
        place_order_btn= findViewById(R.id.place_order_btn);

        cart_recycler_view.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(MyCart.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        cart_recycler_view.setLayoutManager(layoutManager);


        list = new ArrayList<>();
        cartAdapter = new CartAdapter(this, list);
        cart_recycler_view.setAdapter(cartAdapter);

        String shopName = getIntent().getStringExtra("shopName");
        String shopAddress = getIntent().getStringExtra("shopAddress");
        String shopMobile = getIntent().getStringExtra("shopMobile");
        String shopImage = getIntent().getStringExtra("shopImage");
        String shopUid = getIntent().getStringExtra("shopUid");






        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String my_uid = user.getUid();

        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("My Cart").child(my_uid);

        databaseReference0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

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








        place_order_btn.setOnClickListener(v->{


            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference("Shop Orders");

            String buyer_details = my_uid ;
            String order_details = "";

            for(int i=0;i<list.size();i++){

                order_details = "\nItem : "+list.get(i).getItemName()+"\nQuantity : "+list.get(i).itemQuantity+"\nPrice : "+list. get(i).getItemPrice()+"\n----------------------";
                buyer_details = buyer_details + order_details;


             }


            System.out.println(buyer_details);


            HashMap<Object,String> map = new HashMap<>();

            map.put("orderDetails",buyer_details);

            reference.child(shopUid).push().setValue(map);

            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();


        });










    }
}