package com.example.anirudhsharma392.teachercorner;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap mMap;

    private static final int MY_PERMISSION_REQUEST_CODE = 98;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 20;

    private GoogleApiClient mGoogleApiclient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 5;
    DatabaseReference ref;
    private GeoFire geoFire;
    Marker mCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ref= FirebaseDatabase.getInstance().getReference("MY location");
        geoFire =new GeoFire(ref);
        setupLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode)
        {
            case MY_PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    if(checkPlayService())
                    {
                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();
                    }

                }
                break; } }

    private void setupLocation() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
        }
        else {
            if (checkPlayService()) {
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }

    private void displayLocation() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            return;
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiclient);

        if(mLastLocation!=null)
        {
            final double latitude = mLastLocation.getLatitude();
            final double longitude = mLastLocation.getLongitude();
            geoFire.setLocation("IT'S ME", new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if(mCurrent!=null)
                        mCurrent.remove();
                    mCurrent=mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude,longitude))
                            .title("IT'S ME"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),12.0f));
                }
            });
            Toast.makeText(this,"Location changed",Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this,"Can't access location",Toast.LENGTH_SHORT).show();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }


    private void buildGoogleApiClient() {
        mGoogleApiclient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiclient.connect();
    }

    private boolean checkPlayService() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();

            else {
                Toast.makeText(this, "not suported ", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Otp_area = new LatLng(28.485420, 77.309544);
        mMap.addCircle(new CircleOptions()
                .center(Otp_area)
                .fillColor(0x220000FF)
                .strokeColor(Color.BLUE)
                .radius(500)
                .strokeWidth(5.0f));

        GeoQuery geoQuery =geoFire.queryAtLocation(new GeoLocation(Otp_area.latitude,Otp_area.longitude),0.5f);
        geoQuery.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {
            @Override
            public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {
                Intent intent = new Intent(getApplicationContext(),Attendence.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"User entered",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataExited(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),"User Exits",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });

    }

    private void startLocationUpdates() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            return;
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiclient,mLocationRequest, (LocationListener) this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiclient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation=location;
        displayLocation();
    }
}
