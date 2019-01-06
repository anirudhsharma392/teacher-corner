package com.example.anirudhsharma392.teachercorner;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable(){

            public void run(){
                Intent homeintent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(homeintent);
                finish();


            }



        },SPLASH_TIME_OUT);

    }
}
