package com.kristijan_pajtasev.assignment03;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Statistics screen activity
 */
public class StatisticsActivity extends Activity {
    ArrayList<LocationPoint> locationPoints;

    /**
     * Gets all statistic values and shows them in appropriate text views
     * @param savedInstanceState
     */
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
        long totalTime = LocationPointUtil.totalTime(locationPoints);

        ((TextView)findViewById(R.id.minAltitude)).setText(distanceDisplay(minAltitude));
        ((TextView)findViewById(R.id.maxAltitude)).setText(distanceDisplay(maxAltitude));
        ((TextView)findViewById(R.id.altitudeGain)).setText(distanceDisplay(altitudeGain));
        ((TextView)findViewById(R.id.altitudeLoss)).setText(distanceDisplay(altitudeLoss));
        ((TextView)findViewById(R.id.maxSpeed)).setText(speedDisplay(maxSpeed));
        ((TextView)findViewById(R.id.minSpeed)).setText(speedDisplay(minSpeed));
        ((TextView)findViewById(R.id.avgSpeed)).setText(speedDisplay(avgSpeed));
        ((TextView)findViewById(R.id.totalDistance)).setText(distanceDisplay(totalDistance));
        ((TextView)findViewById(R.id.totalTime)).setText(timeDisplay(totalTime));

        ((Chart)findViewById(R.id.chart)).setPoints(locationPoints, maxAltitude, minAltitude);
    }

    /**
     * Creates display String for distance
     * @param distance
     * @return formatted distance string
     */
    private String distanceDisplay(double distance) {
        return String.format("%.2fm", distance);
    }

    /**
     * Creates display String for distance
     * @param time
     * @return formatted time string
     */
    private String timeDisplay(long time) {
        return String.format("%ds", time);
    }

    /**
     * Creates display String for speed
     * @param speed
     * @return formatted speed string
     */
    private String speedDisplay(double speed) {
        return String.format("%.2fm/s", speed);
    }
}
