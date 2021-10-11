package com.example.foodmunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetails extends AppCompatActivity {

    CircleImageView buyer_profile_pic;
    TextView buyer_name,buyer_mobile,order_details,order_total_price,buyer_address;
    String my_uid;
    Button order_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        buyer_profile_pic = findViewById(R.id.buyer_profile_pic);

        buyer_name = findViewById(R.id.buyer_name);
        buyer_mobile = findViewById(R.id.buyer_mobile);
        order_details = findViewById(R.id.order_details);
        order_total_price = findViewById(R.id.order_total_price);

        order_status = findViewById(R.id.order_status);
        buyer_address = findViewById(R.id.buyer_address);




        String buyerName = getIntent().getStringExtra("buyerName");
        String buyerMobile = getIntent().getStringExtra("buyerMobile");
        String buyerImage = getIntent().getStringExtra("buyerImage");
        String orderDetails = getIntent().getStringExtra("orderDetails");
        String totalPrice = getIntent().getStringExtra("totalPrice");
        String buyerAddress = getIntent().getStringExtra("buyerAddress");

       if(buyerImage!=null)
       {
           Picasso.get().load(buyerImage).into(buyer_profile_pic);
       }




        buyer_name.setText(buyerName);
        buyer_mobile.setText(buyerMobile);
        order_details.setText(orderDetails);
        order_total_price.setText("Total Price : "+totalPrice +"৳");
        buyer_address.setText(buyerAddress);





        my_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference0.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    UserModel userModel = dataSnapshot.getValue(UserModel.class);

                    if(userModel.getUid().equals(my_uid)&&userModel.getUserStatus().equals("admin"))
                    {
                        order_status.setVisibility(View.VISIBLE);
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}