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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MyProfile extends AppCompatActivity {

    ImageView profile_edit_icon,profile_image_IV;
    EditText profile_name_TV,profile_mobile_TV,profile_email_TV,profile_address_TV;
    Button profile_save_button;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    Uri imageUri;
    String my_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getSupportActionBar().setTitle("My Profile");

        profile_edit_icon= findViewById(R.id.profile_edit_icon);
        profile_image_IV= findViewById(R.id.profile_image_IV);


        profile_name_TV= findViewById(R.id.profile_name_TV);
        profile_mobile_TV= findViewById(R.id.profile_mobile_TV);
        profile_email_TV= findViewById(R.id.profile_email_TV);
        profile_address_TV= findViewById(R.id.profile_address_TV);


        profile_save_button= findViewById(R.id.profile_save_button);





        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        my_uid=user.getUid();





        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    UserModel userModel = dataSnapshot.getValue(UserModel.class);

                    if(userModel.getUid().equals(my_uid)){

                        String Name = userModel.getName();
                        String Mobile = userModel.getMobile();
                        String Address = userModel.getAddress();
                        String Email = userModel.getEmail();
                        String Status = userModel.getUserStatus();
                        String ImageUrl = userModel.getImageUrl();

                        if(ImageUrl!=null){
                            Picasso.get().load(ImageUrl).into(profile_image_IV);
                        }
                        else
                        {

                        }


                        profile_name_TV.setText(Name);
                        profile_mobile_TV.setText(Mobile);
                        profile_address_TV.setText(Address);
                        profile_email_TV.setText(Email);


                    }



                    }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });














        profile_edit_icon.setOnClickListener(v->{

            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,2);


        });

        profile_save_button.setOnClickListener(v->{

            if(imageUri != null){
                uploadToFirebase(imageUri);
            }else
                Toast.makeText(getApplicationContext(), "Please Select Image ", Toast.LENGTH_SHORT).show();


            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();

        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data!=null){

            imageUri = data.getData();
            profile_image_IV.setImageURI(imageUri);
        }
    }

    void uploadToFirebase(Uri uri){







        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    UserModel userModel = dataSnapshot.getValue(UserModel.class);

                    if(userModel.getUid().equals(my_uid)){

                        String Name = profile_name_TV.getText().toString();
                        String Mobile = userModel.getMobile();
                        String Address = userModel.getAddress();
                        String Email = userModel.getEmail();
                        String Status = userModel.getUserStatus();
                        String ImageUrl = userModel.getImageUrl();


                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










        StorageReference fileRef = reference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {



                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                                    UserModel userModel = dataSnapshot.getValue(UserModel.class);

                                    if(userModel.getUid().equals(my_uid)){

                                        userModel.setImageUrl(uri.toString());

                                       dataSnapshot.getRef().setValue(userModel);

                                        Toast.makeText(getApplicationContext(), "Profile Updated Succesfully", Toast.LENGTH_SHORT).show();

                                    }



                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });





                        Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}