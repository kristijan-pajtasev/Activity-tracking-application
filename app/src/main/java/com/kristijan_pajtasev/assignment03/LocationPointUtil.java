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

    public static double altitudeGain(ArrayList<LocationPoint> locationPoints) {
        double gain = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            double diff = locationPoints.get(i + 1).getAltitude() -
                    locationPoints.get(i).getAltitude();
            if(diff > 0) gain += diff;
        }
        return gain;
    }

    public static double altitudeLoss(ArrayList<LocationPoint> locationPoints) {
        double loss = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            double diff = locationPoints.get(i + 1).getAltitude() -
                    locationPoints.get(i).getAltitude();
            if(diff < 0) loss -= diff;
        }
        return loss;
    }
}
