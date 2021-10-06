package com.example.foodmunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopInfo extends AppCompatActivity {


    CircleImageView shop_info_shop_image;
    TextView shop_info_shop_name,shop_info_shop_Address,shop_info_shop_mobile;
    ImageView shop_info_shop_mobile_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);

        getSupportActionBar().setTitle("Shop Details");

        shop_info_shop_name = findViewById(R.id.shop_info_shop_name);
        shop_info_shop_Address = findViewById(R.id.shop_info_shop_Address);
        shop_info_shop_mobile = findViewById(R.id.shop_info_shop_mobile);

        shop_info_shop_mobile_btn = findViewById(R.id.shop_info_shop_mobile_btn);
        shop_info_shop_image = findViewById(R.id.shop_info_shop_image);


        String shopName = getIntent().getStringExtra("shopName");
        String shopAddress = getIntent().getStringExtra("shopAddress");
        String shopMobile = getIntent().getStringExtra("shopMobile");
        String shopImage = getIntent().getStringExtra("shopImage");

        Picasso.get().load(shopImage).into(shop_info_shop_image);

        shop_info_shop_name.setText(shopName);
        shop_info_shop_Address.setText(shopAddress);
        shop_info_shop_mobile.setText(shopMobile);



        shop_info_shop_mobile_btn.setOnClickListener(v->{

            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + shopMobile));
            startActivity(callIntent);

        });






    }
}