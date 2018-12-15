package com.kristijan_pajtasev.assignment03;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements LocationListener {
    public static final String ACTIVITY_TAG = "MAIN_ACTIVITY";
    private boolean isStarted = false;
    private Button startStopButton;
    private ArrayList<LocationPoint> points;
    private Context context;
    private TextView totalDistance, totalTIme;
    private long time;
    private LocationManager locationManager;

    private OnClickListener startStopButtonHandler = new OnClickListener() {
        @Override
        public void onClick(View v) {
            isStarted = !isStarted;
            if (isStarted) {
                time = System.currentTimeMillis();
                points = new ArrayList<>();
                startActivity();
                startStopButton.setBackgroundResource(R.drawable.round_stop_button);
            } else {
                startStopButton.setBackgroundResource(R.drawable.round_start_button);
                stopActivity();
                if(points.size() < 2) {
                    showNotEnoughPointsPopup();
                } else {
                    goToStats();
                }
            }
        }
    };

    public void showNotEnoughPointsPopup() {
        Toast.makeText(context, R.string.not_enough_points, Toast.LENGTH_LONG).show();
    }

    public void goToStats() {
        GPXHandlerUtil.createFile(points, "myfile.gpx", context);
        Intent intent = new Intent(context, StatisticsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        startStopButton = findViewById(R.id.startStopButton);
        totalDistance = findViewById(R.id.totalDistance);
        totalTIme = findViewById(R.id.totalTime);
        startStopButton.setOnClickListener(startStopButtonHandler);
    }

    public void initialize() {
        totalDistance.setText(getTotalDistanceString(0));
        totalTIme.setText(getTotalTimeString(0));
    }

    private void startActivity() {
        Log.w(ACTIVITY_TAG, "Activity started");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            boolean shouldAskLocationPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
            boolean shouldAskStoragePermission = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (shouldAskLocationPermission) {
                activityRequiresPermissionsToast();
            } else if (shouldAskStoragePermission) {
                activityRequiresPermissionsToast();
            } else {
                // ask for permissions
            }
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.w(ACTIVITY_TAG, "GPS permissions missing");
        } else {
            Log.w(ACTIVITY_TAG, "GPS permissions granted");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        }
    }

    public void stopActivity() {
        locationManager.removeUpdates(this);
    }

    public void activityRequiresPermissionsToast() {
        Toast.makeText(context, R.string.application_requires_permissions, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(ACTIVITY_TAG, "onLocationChanged: " + locationDisplay(location));
        points.add(new LocationPoint(
                location.getAltitude(),
                location.getLatitude(),
                location.getLongitude(),
                location.getTime()));

        Double totalDistance = LocationPointUtil.totalDistance(points);
        String totalDistanceString = getTotalDistanceString(totalDistance);
        this.totalDistance.setText(totalDistanceString);

        String totalTimeString = getTotalTimeString((System.currentTimeMillis() - time) / 1000);
        this.totalTIme.setText(totalTimeString);

    }

    private String getTotalTimeString(long time) {
        long minutes = time / 60;
        long seconds = time % 60;
        if(seconds < 10)
            return String.format("%d:0%d", minutes, seconds);
        return String.format("%d:%d", minutes, seconds);
    }

    private String getTotalDistanceString(double distanace) {
        return String.format("%.2fm", distanace);
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_TAG, "onResume triggered");
        initialize();
    }
}
