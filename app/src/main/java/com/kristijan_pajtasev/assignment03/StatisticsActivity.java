package com.kristijan_pajtasev.assignment03;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        ArrayList<LocationPoint> locationPoints = GPXHandlerUtil.decodeGPX(this, "myfile.gpx");

        double maxAltitude = LocationPointUtil.maxAltitude(locationPoints);
        double minAltitude = LocationPointUtil.minAltitude(locationPoints);
        double altitudeGain = LocationPointUtil.altitudeGain(locationPoints);
        double altitudeLoss = LocationPointUtil.altitudeLoss(locationPoints);
        double maxSpeed = LocationPointUtil.maxSpeed(locationPoints);
        double minSpeed = LocationPointUtil.minSpeed(locationPoints);

        ((TextView)findViewById(R.id.minAltitude)).setText(minAltitude + "");
        ((TextView)findViewById(R.id.maxAltitude)).setText(maxAltitude + "");
        ((TextView)findViewById(R.id.altitudeGain)).setText(altitudeGain + "");
        ((TextView)findViewById(R.id.altitudeLoss)).setText(altitudeLoss + "");
        ((TextView)findViewById(R.id.maxSpeed)).setText(maxSpeed + "");
        ((TextView)findViewById(R.id.minSpeed)).setText(minSpeed + "");
    }
}
