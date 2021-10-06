package com.example.foodmunch;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddItem extends AppCompatActivity {

    CircleImageView add_item_image;
    ImageView add_item_edit_image;
    EditText add_item_name,add_item_description,add_item_price;
    Button add_item_button;
    Uri imageUri;

    String imageUrl;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();


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
            String ItemImage = imageUrl;
            String ShopUid = my_uid;

            ModelItem modelItem=new ModelItem(ItemName,ItemDescription,ItemPrice,ItemImage,ShopUid);

            reference.child(my_uid).push().setValue(modelItem);

            startActivity(new Intent(getApplicationContext(),MyShop.class));
            finish();



        });


        add_item_edit_image.setOnClickListener(v->{

            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,2);


        });






    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==2 && resultCode == RESULT_OK && data!= null);
        {
            imageUri = data.getData();
            add_item_image.setImageURI(imageUri);
        }

        if(imageUri!=null){

            StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+ getFileExt(imageUri));
            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                 imageUrl = uri.toString();

                                System.out.println("======================>>>"+imageUrl);
                                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                            }
                        });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }else{
            Toast.makeText(getApplicationContext(), "Select Image First", Toast.LENGTH_SHORT).show();
        }




    }

    private String getFileExt(Uri mUri){

        ContentResolver cr= getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));


    }
}