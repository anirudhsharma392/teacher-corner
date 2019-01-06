package com.example.anirudhsharma392.teachercorner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    TextView name;
    TextView mail;
    TextView age;
    TextView address;
    TextView phone;



    ProgressBar progressBar3;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        View backgroundImage = findViewById(R.id.constraintLayout3);
        Drawable background = backgroundImage.getBackground();

        background.setAlpha(80);
        progressBar3 =(ProgressBar)findViewById(R.id.progressBar3) ;





        name = (TextView) findViewById(R.id.name_display);
        mail = (TextView)findViewById(R.id.email_display);
        age = (TextView)findViewById(R.id.age_display);
        address = (TextView)findViewById(R.id.address_display);
        phone = (TextView)findViewById(R.id.phone_display);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        display();


    }

    private void display(){




        progressBar3.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile =dataSnapshot.getValue(UserProfile.class);


                name.setText("Name: " + userProfile.getName());
                mail.setText("E-Mail: " + userProfile.getMail());
                age.setText("Age: " + userProfile.getAge());
                address.setText("Address: " + userProfile.getAddress());
                phone.setText("Contact no. : "+ userProfile.getPhone());
                progressBar3.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });




    }


    public void onclick(View v){

        switch (v.getId()){

            case R.id.back_btn:

                Intent intent = new Intent(Profile.this, Home.class);
                startActivity(intent);
                break;



        }



}}
