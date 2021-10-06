package com.example.foodmunch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ItemDetails extends AppCompatActivity {


    ImageView item_details_image;
    TextView item_details_name,item_details_description,item_details_price,item_details_shop_name;
    Button item_details_add_to_my_cart_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        item_details_image=findViewById(R.id.item_details_image);

        item_details_name=findViewById(R.id.item_details_name);
        item_details_description=findViewById(R.id.item_details_description);
        item_details_price=findViewById(R.id.item_details_price);
        item_details_shop_name=findViewById(R.id.item_details_shop_name);

        item_details_add_to_my_cart_button=findViewById(R.id.item_details_add_to_my_cart_button);


        String itemName = getIntent().getStringExtra("itemName");
        String itemDescription = getIntent().getStringExtra("itemDescription");
        String itemPrice = getIntent().getStringExtra("itemPrice");
        String itemImage = getIntent().getStringExtra("itemImage");
        String shopUid = getIntent().getStringExtra("shopUid");


        getSupportActionBar().setTitle(itemName);


        item_details_name.setText(itemName);
        item_details_description.setText(itemDescription);
        item_details_price.setText("৳ "+itemPrice);

        if(!itemImage.equals("")&& itemImage!= null){
            Picasso.get().load(itemImage).into(item_details_image);
        }


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);

                    if (userModel.getUid().equals(shopUid))
                    {
                        item_details_shop_name.setText("\uD83D\uDED2"+userModel.getName());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        item_details_add_to_my_cart_button.setOnClickListener(v->{

            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=database.getReference("My Cart");

            String my_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            CartModel cartModel = new CartModel(itemName,itemDescription,itemImage,itemPrice,shopUid,"1");

            databaseReference.child(my_uid).push().setValue(cartModel);

            Toast.makeText(getApplicationContext(), "Added to cart ", Toast.LENGTH_SHORT).show();



        });






    }
}