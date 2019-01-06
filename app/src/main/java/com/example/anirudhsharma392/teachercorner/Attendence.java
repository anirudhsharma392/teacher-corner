package com.example.anirudhsharma392.teachercorner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Attendence extends AppCompatActivity {

    ProgressBar progressBar4;
    String phone;

    EditText a;
    String codeSent;
    boolean clicked =false;
    String current_date,current_time;

    EditText ed2;
    Helper formdb;







    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        ed2 =(EditText)findViewById(R.id.editText);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        display();




        formdb = new Helper(this);
       Calendar calendar =Calendar.getInstance();
        current_date = DateFormat.getDateInstance().format(calendar.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        current_time = format.format(calendar.getTime());






    }



    private void display(){


        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile =dataSnapshot.getValue(UserProfile.class);


              phone =userProfile.getPhone();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Attendence.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void addata() {
        if (clicked) {
            boolean result = formdb.insertData(current_date.toString(), current_time.toString());
            if (result == true) {
                Toast.makeText(getApplicationContext(), "Attendance Recorded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Attendence.this, "No Data Found ", Toast.LENGTH_SHORT).show(); }

        } else {
            Toast.makeText(getApplicationContext(), "Please Verify the Code", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onStart()
    {

        super.onStart();
        formdb.openDb();
    }


    protected void onStop()
    {
        super.onStop();
        formdb.closeDb();
    }





    private void sendcode()
    {




        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
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
            Toast.makeText(Attendence.this, "OTP has been sent on Phone no. : "+phone, Toast.LENGTH_SHORT).show();
        }
    };




    private void verifycode()
    {
        String code = ed2.getText().toString().trim();
        if(code.isEmpty()){

            ed2.setError("Please Enter OTP");
            ed2.requestFocus();
            return;


        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),"OTP Verified ",Toast.LENGTH_SHORT).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(getApplicationContext(),"invalid code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void onclick(View v){

        switch (v.getId()){


            case R.id.recieveotp:
                sendcode();
                break;

            case R.id.verifyotp:


                clicked =true;
                if(clicked){

                    verifycode();
                }

                break;

            case R.id.mark:

                addata();
                break;

        }


}


}
