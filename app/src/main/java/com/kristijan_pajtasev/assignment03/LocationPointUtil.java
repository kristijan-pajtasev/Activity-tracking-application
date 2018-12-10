package com.kristijan_pajtasev.assignment03;

import java.util.ArrayList;

public class LocationPointUtil {
    public static double maxAltitude(ArrayList<LocationPoint> locationPoints) {
        double maxAltitude = locationPoints.get(0).getAltitude();
        if(locationPoints.size() > 1) {
            for(int i = 1; i < locationPoints.size(); i++) {
                double alt = locationPoints.get(i).getAltitude();
                if(alt > maxAltitude) maxAltitude = alt;
            }
        }
        return maxAltitude;
    }

    public static double minAltitude(ArrayList<LocationPoint> locationPoints) {
        double maxAltitude = locationPoints.get(0).getAltitude();
        if(locationPoints.size() > 1) {
            for(int i = 1; i < locationPoints.size(); i++) {
                double alt = locationPoints.get(i).getAltitude();
                if(alt < maxAltitude) maxAltitude = alt;
            }
        }
        return maxAltitude;
    }
}
