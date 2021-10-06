package com.example.foodmunch;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class ShopItemList extends AppCompatActivity {

    RecyclerView shop_item_list_recyclerview;
    AdapterItem adapterItem;
    ArrayList<ModelItem> list;
    String SN = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item_list);


        shop_item_list_recyclerview = findViewById(R.id.shop_item_list_recyclerview);


        String shopName = getIntent().getStringExtra("shopName");
        String shopAddress = getIntent().getStringExtra("shopAddress");
        String shopMobile = getIntent().getStringExtra("shopMobile");
        String shopImage = getIntent().getStringExtra("shopImage");
        String shopUid = getIntent().getStringExtra("shopUid");

        SN = shopName;


        getSupportActionBar().setTitle(shopName);


        LinearLayoutManager layoutManager = new LinearLayoutManager(ShopItemList.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        shop_item_list_recyclerview.setLayoutManager(layoutManager);


        list = new ArrayList<>();
        adapterItem = new AdapterItem(this, list);
        shop_item_list_recyclerview.setAdapter(adapterItem);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String my_uid = user.getUid();

        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("Item List").child(my_uid);

        databaseReference0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ModelItem modelItem = dataSnapshot.getValue(ModelItem.class);

                    if (modelItem.getShopUid().equals(shopUid))
                        list.add(modelItem);


                }

                adapterItem.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_cart:
                startActivity( new Intent(getApplicationContext(),MyCart.class));
                return true;
            case R.id.search_item:
                Toast.makeText(getApplicationContext(), "Search Clicked", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return true;
        }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("If you go back your cart will be cleared from \uD83D\uDED2"+ SN +".\nAre you sure to go back?")
            .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String my_uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("My Cart").child(my_uid);

                databaseReference.removeValue();

                startActivity(new Intent(getApplicationContext(),HomePage.class));
                finish();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();





    }
}