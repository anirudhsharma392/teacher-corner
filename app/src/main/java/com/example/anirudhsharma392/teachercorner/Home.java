package com.example.anirudhsharma392.teachercorner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseauth = FirebaseAuth.getInstance();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.logoutmenu:
                firebaseauth.signOut();
                finish();
                Intent intent = new Intent(Home.this,LoginPage.class);

                startActivity(intent);

            case R.id.picmenu:

        }return super.onOptionsItemSelected(item);
    }

    public void onclick(View v){

        switch (v.getId()){


            case R.id.PROFILE:
                Intent intent = new Intent(Home.this,Profile.class);

                startActivity(intent);
                break;

            case R.id.ATTENDENCE:

                Intent iintent = new Intent(Home.this,MapsActivity.class);

                startActivity(iintent);
                break;

            case R.id.EVENTS:


                Intent iiintent = new Intent(Home.this,Events.class);

                startActivity(iiintent);


                break;


        }
}



}
