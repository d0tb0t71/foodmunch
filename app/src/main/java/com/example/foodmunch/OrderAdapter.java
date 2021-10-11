package com.example.foodmunch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<ModelOrder> list;

    String buyerName, buyerMobile, buyerImage,totalPrice,buyerAddress;


    public OrderAdapter(Context context, ArrayList<ModelOrder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_single_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        ModelOrder modelOrder = list.get(position);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference0.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    UserModel userModel = dataSnapshot.getValue(UserModel.class);

                    System.out.println(userModel.getName() + "=======================================");

                    if (userModel.getUid().equals(modelOrder.getBuyerUid())) {
                        holder.buyer_name_TV.setText(userModel.getName());
                        buyerName = userModel.getName();

                        holder.buyer_mobile_TV.setText("Mobile : " + userModel.getMobile());
                        buyerMobile = userModel.getMobile();

                        holder.buyer_address_TV.setText(userModel.getAddress());
                        buyerAddress = userModel.getAddress();


                        buyerImage = userModel.getImageUrl();

                        totalPrice = modelOrder.getTotalPrice();


                        if (modelOrder.getOrderStatus().equals("Cancel")) {
                            holder.orders_single_view.setBackgroundColor(Color.RED);
                        } else if (modelOrder.getOrderStatus().equals("Done")) {
                            holder.orders_single_view.setBackgroundColor(Color.GREEN);
                        }
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.buyer_name_TV.setOnClickListener(v -> {

            startOrderDetails(buyerName, buyerMobile, buyerImage, modelOrder.getOrderDetails(),totalPrice,buyerAddress);

        });
        holder.buyer_mobile_TV.setOnClickListener(v -> {

            startOrderDetails(buyerName, buyerMobile, buyerImage, modelOrder.getOrderDetails(),totalPrice,buyerAddress);

        });
        holder.orders_single_view.setOnClickListener(v -> {

            startOrderDetails(buyerName, buyerMobile, buyerImage, modelOrder.getOrderDetails(),totalPrice,buyerAddress);
        });
        holder.buyer_address_TV.setOnClickListener(v -> {

            startOrderDetails(buyerName, buyerMobile, buyerImage, modelOrder.getOrderDetails(),totalPrice,buyerAddress);
        });


    }

    void startOrderDetails(String buyerName, String buyerMobile, String buyerImage, String orderDetails,String totalPrice,String buyerAddress) {

        Intent intent = new Intent(context.getApplicationContext(), OrderDetails.class);

        intent.putExtra("buyerName", buyerName);
        intent.putExtra("buyerMobile", buyerMobile);
        intent.putExtra("buyerImage", buyerImage);
        intent.putExtra("orderDetails", orderDetails);
        intent.putExtra("totalPrice", totalPrice);
        intent.putExtra("buyerAddress", buyerAddress);

        context.startActivity(intent);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView buyer_name_TV, buyer_mobile_TV,buyer_address_TV;
        RelativeLayout orders_single_view;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            buyer_name_TV = itemView.findViewById(R.id.buyer_name_TV);
            buyer_address_TV = itemView.findViewById(R.id.buyer_address_TV);
            buyer_mobile_TV = itemView.findViewById(R.id.buyer_mobile_TV);
            orders_single_view = itemView.findViewById(R.id.orders_single_view);

        }
    }
}
