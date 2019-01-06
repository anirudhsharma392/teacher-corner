package com.example.anirudhsharma392.teachercorner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {

    EditText ed1,ed2;
    Button b1,b2;
    String codeSent;
     FirebaseAuth mAuth;
     FirebaseAuth firebaseAuth;
     FirebaseDatabase firebaseDatabase;


     String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mAuth= FirebaseAuth.getInstance();


        View backgroundImage = findViewById(R.id.layout);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(100);

        display();

        ed1= (EditText)findViewById(R.id.phone);
        ed2= (EditText)findViewById(R.id.otp);
        b1 =(Button)findViewById(R.id.reset_btn);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendcode();
            }
        });

        b2 =(Button)findViewById(R.id.verify_btn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifycode();

            }
        });




    }




    private void sendcode()
    {
        String phoneNumber = ed1.getText().toString().trim();
        if(phoneNumber.isEmpty())
        {
            ed1.setError("Number is required");
            ed1.requestFocus();
            return;
        }
        if(phoneNumber.length()<10)
        {
            ed1.setError("Number is required");
            ed1.requestFocus();
            return;

        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent =s;
        }
    };

    private void verifycode()
    {

        String code = ed2.getText().toString().trim();
        if(code.isEmpty()){

            ed1.setError("Please Enter OTP");
            ed1.requestFocus();
            return;


        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),"Phone no. Registered ",Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(otp.this,LoginPage.class);
                            startActivity(intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(getApplicationContext(),"invalid code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void display(){



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile =dataSnapshot.getValue(UserProfile.class);



                ed1.setText(userProfile.getPhone());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(otp.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });




    }




}
