package com.example.foodmunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    ArrayList<CartModel> list;



    public CartAdapter(Context context, ArrayList<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_product_list,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        CartModel cartModel = list.get(position);

        holder.cart_item_name.setText(cartModel.getItemName());
        holder.cart_item_price.setText("৳ "+cartModel.getItemPrice());
        holder.cart_item_quantity.setText(cartModel.getItemQuantity());


        if(!cartModel.getItemImage().equals("")&& cartModel.getItemImage()!= null){
            Picasso.get().load(cartModel.getItemImage()).into(holder.cart_item_image);
        }




        int price = Integer.parseInt(cartModel.getItemPrice());

        holder.cart_item_plus.setOnClickListener(v->{

            int tprice = Integer.parseInt(cartModel.getItemPrice()) + price;
            int quan = Integer.parseInt(cartModel.getItemQuantity())+1;

            cartModel.setItemPrice(String.valueOf(tprice));
            cartModel.setItemQuantity(String.valueOf(quan));

            holder.cart_item_quantity.setText(cartModel.getItemQuantity());
            holder.cart_item_price.setText("৳ "+cartModel.getItemPrice());

        });

        holder.cart_item_minus.setOnClickListener(v->{

            int quan = Integer.parseInt(cartModel.getItemQuantity())-1;
            if(quan<=0)
                quan=1;

            int tprice = Integer.parseInt(cartModel.getItemPrice()) - price;

            if(quan==1)
                tprice=price;

            cartModel.setItemPrice(String.valueOf(tprice));
            cartModel.setItemQuantity(String.valueOf(quan));

            holder.cart_item_quantity.setText(cartModel.getItemQuantity());
            holder.cart_item_price.setText("৳ "+cartModel.getItemPrice());


        });



    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cart_item_name,cart_item_price,cart_item_quantity;
        ImageView cart_item_minus,cart_item_plus,cart_item_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cart_item_name = itemView.findViewById(R.id.cart_item_name);
            cart_item_price = itemView.findViewById(R.id.cart_item_price);
            cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);


            cart_item_minus = itemView.findViewById(R.id.cart_item_minus);
            cart_item_plus = itemView.findViewById(R.id.cart_item_plus);
            cart_item_image = itemView.findViewById(R.id.cart_item_image);






        }
    }
}
