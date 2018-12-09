package com.kristijan_pajtasev.assignment03;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private boolean isStarted = false;
    private Button startStopButton;
    private TextView currentLocation;

    private OnClickListener startStopButtonHandler = new OnClickListener() {
        @Override
        public void onClick(View v) {
            isStarted = !isStarted;
            if (isStarted) {
                startActivity();
                startStopButton.setText(R.string.stop);
            } else {
                startStopButton.setText(R.string.start);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startStopButton = findViewById(R.id.startStopButton);
        currentLocation = findViewById(R.id.currentLocation);
        startStopButton.setOnClickListener(startStopButtonHandler);
    }

    private void startActivity() {
        Log.w("MAIN_ACTIVITY", "Activity started");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            }
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.w("MAIN_ACTIVITY", "GPS permissions missing");
        } else {
            Log.w("MAIN_ACTIVITY", "GPS permissions granted");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("MAIN_ACTIVITY", "onLocationChanged: " + locationDisplay(location));
        currentLocation.setText(locationDisplay(location));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private String locationDisplay(Location location) {
        return "(lat: " + location.getLatitude() + ", long: " + location.getLongitude() +
                ", alt: " + location.getAltitude() + ")";
    }

}
