package com.example.foodmunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.MyViewHolder> {

    Context context;
    ArrayList<UserModel> list;

    public AdapterShop(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_list_single_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserModel userModel= list.get(position);

        holder.shop_name_TV.setText(userModel.getName());
        holder.shop_address_TV.setText(userModel.getAddress());
        holder.shop_mobile_TV.setText(userModel.getMobile());

        Picasso.get().load(userModel.getImageUrl()).into(holder.shop_image_IV);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView shop_name_TV,shop_address_TV,shop_mobile_TV;
        CircleImageView shop_image_IV;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            shop_name_TV = itemView.findViewById(R.id.shop_name_TV);
            shop_address_TV = itemView.findViewById(R.id.shop_address_TV);
            shop_mobile_TV = itemView.findViewById(R.id.shop_mobile_TV);
            shop_image_IV = itemView.findViewById(R.id.shop_image_IV);

        }
    }
}
