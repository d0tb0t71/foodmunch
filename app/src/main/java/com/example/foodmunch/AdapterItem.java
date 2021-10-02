package com.example.foodmunch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.MyViewHolder> {

    Context context;
    ArrayList<ModelItem> list;

    public AdapterItem(Context context, ArrayList<ModelItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_shop_product_single_view,parent,false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelItem modelItem = list.get(position);

        holder.item_name.setText(modelItem.getItemName());
        holder.item_description.setText(modelItem.getItemDescription());
        holder.item_price.setText("৳ "+modelItem.getItemPrice());

        if(!modelItem.getItemImage().equals("")) {
            Picasso.get().load(modelItem.getItemImage()).into(holder.item_image);
        }


        DatabaseReference databaseReference0 = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference0.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    if(userModel.getUid().equals(modelItem.getShopUid())){
                        holder.shop_name.setText(userModel.getName());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        holder.item_name.setOnClickListener(v->{
            startItemDetails(modelItem.getItemName(),modelItem.getItemDescription(),modelItem.getItemPrice(),modelItem.getItemImage(),modelItem.getShopUid());
        });
         holder.item_description.setOnClickListener(v->{
             startItemDetails(modelItem.getItemName(),modelItem.getItemDescription(),modelItem.getItemPrice(),modelItem.getItemImage(),modelItem.getShopUid());
        });
         holder.item_price.setOnClickListener(v->{
             startItemDetails(modelItem.getItemName(),modelItem.getItemDescription(),modelItem.getItemPrice(),modelItem.getItemImage(),modelItem.getShopUid());
         });
         holder.my_shop_product_layout.setOnClickListener(v->{
             startItemDetails(modelItem.getItemName(),modelItem.getItemDescription(),modelItem.getItemPrice(),modelItem.getItemImage(),modelItem.getShopUid());
         });


    }

    private void startItemDetails(String itemName, String itemDescription, String itemPrice, String itemImage,String shopUid) {

        Intent intent = new Intent(context.getApplicationContext(), ItemDetails.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("itemDescription", itemDescription);
        intent.putExtra("itemPrice", itemPrice);
        intent.putExtra("itemImage", itemImage);
        intent.putExtra("shopUid", shopUid);
        intent.putExtra("itemImage", itemImage);

        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView item_name,shop_name,item_description,item_price;
    CircleImageView item_image;
    RelativeLayout my_shop_product_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            shop_name = itemView.findViewById(R.id.shop_name);
            item_description = itemView.findViewById(R.id.item_description);
            item_price = itemView.findViewById(R.id.item_price);


            item_image = itemView.findViewById(R.id.item_image);
            my_shop_product_layout = itemView.findViewById(R.id.my_shop_product_layout);




        }
    }
}
