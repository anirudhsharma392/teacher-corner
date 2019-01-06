package com.example.anirudhsharma392.teachercorner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {


    EditText username_signup;
    EditText name_signup;
    EditText password_signup;
    EditText age_signup;
    EditText address_signup;
    EditText phone_signup;

    ProgressBar progressBar;
    String username,name,age,address,phone;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        age_signup = (EditText)findViewById(R.id.age_signup);
        address_signup = (EditText)findViewById(R.id.address_signup);
        name_signup = (EditText)findViewById(R.id.name_signup);
        username_signup = (EditText)findViewById(R.id.email_signup);
        password_signup = (EditText)findViewById(R.id.password_signup);
        phone_signup = (EditText)findViewById(R.id.phone);

        progressBar =(ProgressBar)findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();
    }





    public void registerUser(){


        phone = phone_signup.getText().toString().trim();
        address = address_signup.getText().toString();
        age = age_signup.getText().toString();
        name = name_signup.getText().toString();
         username = username_signup.getText().toString().trim();
        String password = password_signup.getText().toString().trim();



        if(!Patterns.PHONE.matcher(phone).matches()){

            phone_signup.setError("Phone number is not correct");
            phone_signup.requestFocus();


        }

        if(phone.length()<10)
        {
            phone_signup.setError("Correct Number is required");
            phone_signup.requestFocus();
            return;

        }
        if(phone.isEmpty()){

            phone_signup.setError("Contact number required");
            phone_signup.requestFocus();
            return;
        }

        if(address.isEmpty()){

            address_signup.setError("Address is required");
            address_signup.requestFocus();
            return;
        }

        if(age.isEmpty()){

            age_signup.setError("Age is required");
            age_signup.requestFocus();
            return;
        }

        if(name.isEmpty()){

            name_signup.setError("Name is required");
            name_signup.requestFocus();
            return;
        }


        if(username.isEmpty()){

            username_signup.setError("Email is required");
            username_signup.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){

            username_signup.setError("Email id is not valid");
            username_signup.requestFocus();

            return;

        }
        if(password.length()<=6){

            password_signup.setError("Password is short");
            password_signup.requestFocus();
            return;
        }
        if(password.isEmpty()){

            password_signup.setError("Password is required");
            password_signup.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){


                    senduserdata();
                    sendemailverification();


                }else{
                    progressBar.setVisibility(View.GONE);
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){

                        Toast.makeText(SignupPage.this, "User is already Registered", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(SignupPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void sendemailverification(){

        FirebaseUser firebaseUser =mAuth.getCurrentUser();
        if(firebaseUser != null){

            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){



                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(SignupPage.this, "User Registered and Verification Email has been sent!", Toast.LENGTH_SHORT).show();
                        finish();

                        Intent intent = new Intent(SignupPage.this, otp.class);
                        startActivity(intent);

                    }else{

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SignupPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


    }

    private void senduserdata(){


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference data = firebaseDatabase.getReference(mAuth.getUid());

        UserProfile userprofile = new UserProfile(name,username,age,address,phone);

        data.setValue(userprofile);



    }

    public void onclick(View v){

        switch (v.getId()){

            case R.id.signup_btn:
                registerUser();
                break;

            case R.id.login_page:
                finish();
                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);

        }



    }
}


