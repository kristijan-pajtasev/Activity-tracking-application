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

/**
 * MainActivity is Activity class of main, first screen of application. Implements LocationListener
 * to be able to use position change listener
 */
public class MainActivity extends Activity implements LocationListener {
    public static final String ACTIVITY_TAG = "MAIN_ACTIVITY";
    public static final int REQUEST_CODE = 0;
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

    /**
     * Shows toast message if not enough GPS points are takes
     */
    public void showNotEnoughPointsPopup() {
        Toast.makeText(context, R.string.not_enough_points, Toast.LENGTH_LONG).show();
    }

    /**
     * Starts new intent to display all statistics from last activity
     */
    public void goToStats() {
        GPXHandlerUtil.createFile(points, "myfile.gpx", context);
        Intent intent = new Intent(context, StatisticsActivity.class);
        startActivity(intent);
    }

    /**
     * Override of on create method, gets all elements and registers button listener.
     * @param savedInstanceState
     */
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

    /**
     * Sets initial status values of distance and time
     */
    public void initialize() {
        totalDistance.setText(getTotalDistanceString(0));
        totalTIme.setText(getTotalTimeString(0));
    }

    /**
     * Checks if location and storage permissions are added. If not asks for them, otherwise shows
     * toast message requesting for enabling them.
     */
    private void startActivity() {
        Log.w(ACTIVITY_TAG, "Activity started");
        startStopButton.setBackgroundResource(R.drawable.round_stop_button);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            boolean shouldAskLocationPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
            boolean shouldAskStoragePermission = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (shouldAskLocationPermission) {
                activityRequiresPermissionsToast();
            } else if (shouldAskStoragePermission) {
                activityRequiresPermissionsToast();
            } else {
                askForPermissions();
            }
            Log.w(ACTIVITY_TAG, "GPS permissions missing");
        } else {
            Log.w(ACTIVITY_TAG, "GPS permissions granted");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        }
    }

    /**
     * Removes location change listener
     */
    public void stopActivity() {
        locationManager.removeUpdates(this);
    }

    /**
     * Checks if permissions are granted if changed. If yes starts activity, otherwise shows toast
     * message
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    startActivity();
                } else {
                    activityRequiresPermissionsToast();
                }
                return;
            }
        }
    }

    /**
     * Shows prompt asking for permissions
     */
    public void askForPermissions() {
        String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    /**
     * Shows toast message if permissions are already denied
     */
    public void activityRequiresPermissionsToast() {
        Toast.makeText(context, R.string.application_requires_permissions, Toast.LENGTH_LONG).show();
        finish();
    }

    /**
     * On location change listener. When received, converts points to LocationPoint object and adds
     * it to list. Updates status values
     * @param location
     */
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

    /**
     * Gets String format for time
     * @param time
     * @return String formatted value
     */
    private String getTotalTimeString(long time) {
        long minutes = time / 60;
        long seconds = time % 60;
        if(seconds < 10)
            return String.format("%d:0%d", minutes, seconds);
        return String.format("%d:%d", minutes, seconds);
    }


    /**
     * Gets String format for distance passed
     * @param distanace
     * @return String formatted distance
     */
    private String getTotalDistanceString(double distanace) {
        return String.format("%.2fm", distanace);
    }

    /**
     * Inherited unused method
     * @param provider
     * @param status
     * @param extras
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Inherited unused method
     * @param provider
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Inherited unused method
     * @param provider
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Converts location point to formatted string
     * @param location
     * @return String formatted location
     */
    private String locationDisplay(Location location) {
        return "(lat: " + location.getLatitude() + ", long: " + location.getLongitude() +
                ", alt: " + location.getAltitude() + ")";
    }

    /**
     * Re-initializes activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_TAG, "onResume triggered");
        initialize();
    }

    /**
     * Stops location change updates listener
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_TAG, "onDestroy stop gps updates");
        locationManager.removeUpdates(this);
        locationManager = null;
    }
}
