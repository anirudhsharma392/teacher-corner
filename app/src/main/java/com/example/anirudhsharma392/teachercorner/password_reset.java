package com.example.anirudhsharma392.teachercorner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class password_reset extends AppCompatActivity {

    EditText passwordEmail;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        passwordEmail = (EditText)findViewById(R.id.passwordemail);
        mAuth = FirebaseAuth.getInstance();
        progressBar =(ProgressBar)findViewById(R.id.progressbar4);
    }


    private void resetpassword(){


        progressBar.setVisibility(View.VISIBLE);
        String usermail = passwordEmail.getText().toString().trim();


        if(usermail.equals("")){

            Toast.makeText(this, "Please enter your Registered Email Id", Toast.LENGTH_SHORT).show();

        }else{

            mAuth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){


                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(password_reset.this, "Password reset request has been sent to your registered Email", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent =new Intent(password_reset.this,LoginPage.class);
                        startActivity(intent);


                    }else{

                        Toast.makeText(password_reset.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        
                    }
                }
            });


        }




    }

    public void onclick(View v){

        switch (v.getId()){

            case R.id.reset_btn:
                resetpassword();

                break;

            case R.id.back_btn:
                finish();
                Intent intent =new Intent(password_reset.this,LoginPage.class);
                startActivity(intent);

                break;

        }



    }}


