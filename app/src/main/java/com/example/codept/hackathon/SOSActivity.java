package com.example.codept.hackathon;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SOSActivity extends AppCompatActivity {

    private GPSTracker gps;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        Button b = (Button) findViewById(R.id.button2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                getLocation();


            }
        });


    }

        void getLocation() {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            } else {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null){
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();

                   // ((EditText)findViewById(R.id.etLocationLat)).setText("Latitude: " + latti);
                   // ((EditText)findViewById(R.id.etLocationLong)).setText("Longitude: " + longi);

                    Toast.makeText(SOSActivity.this,String.valueOf(latti+longi),Toast.LENGTH_SHORT).show();


                } else {
                   // ((EditText)findViewById(R.id.etLocationLat)).setText("Unable to find correct location.");
                   // ((EditText)findViewById(R.id.etLocationLong)).setText("Unable to find correct location. ");
                    Toast.makeText(SOSActivity.this,"Unable to find",Toast.LENGTH_SHORT).show();


                }
            }

        }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            switch (requestCode) {
                case REQUEST_LOCATION:
                    getLocation();
                    break;
            }
        }
    }