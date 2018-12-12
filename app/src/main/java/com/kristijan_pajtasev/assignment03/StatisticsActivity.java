package com.kristijan_pajtasev.assignment03;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsActivity extends Activity {
    ArrayList<LocationPoint> locationPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        locationPoints = GPXHandlerUtil.decodeGPX(this, "myfile.gpx");

        double maxAltitude = LocationPointUtil.maxAltitude(locationPoints);
        double minAltitude = LocationPointUtil.minAltitude(locationPoints);
        double altitudeGain = LocationPointUtil.altitudeGain(locationPoints);
        double altitudeLoss = LocationPointUtil.altitudeLoss(locationPoints);
        double maxSpeed = LocationPointUtil.maxSpeed(locationPoints);
        double minSpeed = LocationPointUtil.minSpeed(locationPoints);
        double avgSpeed = LocationPointUtil.avgSpeed(locationPoints);
        double totalDistance = LocationPointUtil.totalDistance(locationPoints);

        ((TextView)findViewById(R.id.minAltitude)).setText(distanceDisplay(minAltitude));
        ((TextView)findViewById(R.id.maxAltitude)).setText(distanceDisplay(maxAltitude));
        ((TextView)findViewById(R.id.altitudeGain)).setText(distanceDisplay(altitudeGain));
        ((TextView)findViewById(R.id.altitudeLoss)).setText(distanceDisplay(altitudeLoss));
        ((TextView)findViewById(R.id.maxSpeed)).setText(speedDisplay(maxSpeed));
        ((TextView)findViewById(R.id.minSpeed)).setText(speedDisplay(minSpeed));
        ((TextView)findViewById(R.id.avgSpeed)).setText(speedDisplay(avgSpeed));
        ((TextView)findViewById(R.id.totalDistance)).setText(distanceDisplay(totalDistance));

        ((Chart)findViewById(R.id.chart)).setPoints(locationPoints, maxAltitude, minAltitude);
    }

    private String distanceDisplay(double distance) {
        return String.format("%.2fm", distance);
    }

    private String timeDisplay(long time) {
        return String.format("%ds", time);
    }

    private String speedDisplay(double speed) {
        return String.format("%.2fm/s", speed);
    }
}
