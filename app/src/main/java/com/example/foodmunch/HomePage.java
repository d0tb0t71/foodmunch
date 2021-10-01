package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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

public class HomePage extends AppCompatActivity {

    RecyclerView shop_recyclerview;
    AdapterShop adapterShop;
    ArrayList<UserModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        shop_recyclerview = findViewById(R.id.shop_recyclerview);


        shop_recyclerview.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(HomePage.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        shop_recyclerview.setLayoutManager(layoutManager);


        list = new ArrayList<>();
        adapterShop = new AdapterShop(this, list);
        shop_recyclerview.setAdapter(adapterShop);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String my_uid = user.getUid();

        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference0.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    UserModel userModel = dataSnapshot.getValue(UserModel.class);

                    System.out.println(userModel.getUserStatus()+"-------------------------");



                    if(userModel.getUserStatus().equals("admin")){
                        list.add(userModel);
                    }


                }

                adapterShop.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


















    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_shop:
                Toast.makeText(getApplicationContext(), "Search Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.my_cart:
                startActivity( new Intent(getApplicationContext(),MyCart.class));
                return true;
            case R.id.my_shop:
                startActivity( new Intent(getApplicationContext(),MyShop.class));
                return true;
            case R.id.my_profile:
                startActivity( new Intent(getApplicationContext(),MyProfile.class));
                return true;
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "Log out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                return true;
            default:
                return true;
        }
    }
}