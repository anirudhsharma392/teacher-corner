package com.example.anirudhsharma392.teachercorner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText username;
    EditText password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        View backgroundImage = findViewById(R.id.constraintLayout);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(100);

        mAuth = FirebaseAuth.getInstance();

        username = (EditText)findViewById(R.id.name_signup);
        password = (EditText)findViewById(R.id.password_signup);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);
    }


    public void userlogin(){



        String user= username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(user.isEmpty()){

            username.setError("E-mail is required");

            username.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){

            username.setError("Email id is not valid");
            username.requestFocus();

            return;

        }
        if(pass.length()<=6){

            password.setError("Password is short");
            password.requestFocus();
            return;
        }
        if(pass.isEmpty()){

            password.setError("Password is required");
            password.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    checkemailverification();


                } else{
                    Toast.makeText(LoginPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });





    }

    private void checkemailverification(){

        FirebaseUser firebaseuser = mAuth.getCurrentUser();
        Boolean emailflag = firebaseuser.isEmailVerified();

        if(emailflag){

            finish();
            Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginPage.this,Home.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


        }else{

            Toast.makeText(this, "E-mail not Verified", Toast.LENGTH_SHORT).show();
            mAuth.signOut();


        }


    }



    public void onclick(View v){

        switch (v.getId()){

            case R.id.register:

                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
                break;

            case R.id.login_btn:
                userlogin();

                break;

          case R.id.reset:



             finish();
               Intent iintent =new Intent(LoginPage.this,password_reset.class);
               startActivity(iintent);

        }


}





}
