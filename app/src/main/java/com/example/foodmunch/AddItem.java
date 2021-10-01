package com.example.foodmunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddItem extends AppCompatActivity {

    CircleImageView add_item_image;
    ImageView add_item_edit_image;
    EditText add_item_name,add_item_description,add_item_price;
    Button add_item_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        add_item_image = findViewById(R.id.add_item_image);

        add_item_edit_image = findViewById(R.id.add_item_edit_image);

        add_item_name = findViewById(R.id.add_item_name);
        add_item_description = findViewById(R.id.add_item_description);
        add_item_price = findViewById(R.id.add_item_price);

        add_item_button = findViewById(R.id.add_item_button);


        add_item_button.setOnClickListener(v->{

            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            String my_uid = user.getUid();

            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference("Item List");


            String ItemName = add_item_name.getText().toString();
            String ItemDescription = add_item_description.getText().toString();
            String ItemPrice = add_item_price.getText().toString();
            String ItemImage = "";
            String ShopUid = my_uid;

            ModelItem modelItem=new ModelItem(ItemName,ItemDescription,ItemPrice,ItemImage,ShopUid);


            reference.child(my_uid).push().setValue(modelItem);

            startActivity(new Intent(getApplicationContext(),MyShop.class));
            finish();



        });












    }
}