package com.kristijan_pajtasev.assignment03;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class StatisticsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        ArrayList<LocationPoint> locationPoints = GPXHandlerUtil.decodeGPX(this, "myfile.gpx");

        double maxAltitude = LocationPointUtil.maxAltitude(locationPoints);
        double minAltitude = LocationPointUtil.minAltitude(locationPoints);
    }
}
