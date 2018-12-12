package com.kristijan_pajtasev.assignment03;

import android.location.Location;

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
            double altCurrent = locationPoints.get(i).getAltitude();
            double altNext = locationPoints.get(i + 1).getAltitude();
            if(altCurrent < altNext) gain += (altNext - altCurrent);
        }
        return gain;
    }

    public static double altitudeLoss(ArrayList<LocationPoint> locationPoints) {
        double loss = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            double altCurrent = locationPoints.get(i).getAltitude();
            double altNext = locationPoints.get(i + 1).getAltitude();
            if(altCurrent > altNext) loss += (altCurrent - altNext);
        }
        return loss;
    }

    public static double maxSpeed(ArrayList<LocationPoint> locationPoints) {
        if(locationPoints.size() < 2) return 0;
        double maxSpeed = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            LocationPoint current = locationPoints.get(i);
            LocationPoint next = locationPoints.get(i + 1);

            double speed = distanceBetweenPoints(current, next) / 5;

            if(speed > maxSpeed) maxSpeed = speed;
        }
        return maxSpeed;
    }

    public static double minSpeed(ArrayList<LocationPoint> locationPoints) {
        if(locationPoints.size() < 2) return 0;
        double minSpeed = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            LocationPoint current = locationPoints.get(i);
            LocationPoint next = locationPoints.get(i + 1);

            double speed = distanceBetweenPoints(current, next) / 5;
            if(speed < minSpeed || i == 0) minSpeed = speed;
        }
        return minSpeed;
    }

    public static double avgSpeed(ArrayList<LocationPoint> locationPoints) {
        if(locationPoints.size() < 2) return 0;
        double distance = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            LocationPoint current = locationPoints.get(i);
            LocationPoint next = locationPoints.get(i + 1);

            distance += distanceBetweenPoints(current, next);
        }
        return distance / ((locationPoints.size() - 1) * 5);
    }

    public static double totalDistance(ArrayList<LocationPoint> locationPoints) {
        if(locationPoints.size() < 2) return 0;
        double distance = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            LocationPoint current = locationPoints.get(i);
            LocationPoint next = locationPoints.get(i + 1);

            distance += distanceBetweenPoints(current, next);
        }
        return distance;
    }

    private static double distanceBetweenPoints(LocationPoint point1, LocationPoint point2) {
        Location pointA = new Location("A");
        pointA.setAltitude(point1.getAltitude());
        pointA.setLatitude(point1.getLatitude());
        pointA.setLongitude(point1.getLongitude());
        pointA.setTime(point1.getTime());

        Location pointB = new Location("B");
        pointB.setAltitude(point2.getAltitude());
        pointB.setLatitude(point2.getLatitude());
        pointB.setLongitude(point2.getLongitude());
        pointB.setTime(point2.getTime());

        return pointA.distanceTo(pointB);
    }

    public static float[] toChartPoints(ArrayList<LocationPoint> locationPoints, float scalarX, float scalarY, float yCorrection, float windowHeight) {
        ArrayList<Float> pointsList = new ArrayList<>();
        for(int i = 0; i < locationPoints.size(); i++) {
            LocationPoint point = locationPoints.get(i);
            pointsList.add(i * scalarX);
            pointsList.add(windowHeight - (float)(point.getAltitude() - yCorrection) * scalarY + 10);
            if(i>0 && i < locationPoints.size() - 1) {
                pointsList.add(i * scalarX);
                pointsList.add(windowHeight - (float)(point.getAltitude() - yCorrection) * scalarY + 10);
            }
        }
        float[] points = new float[pointsList.size()];
        for(int i = 0; i < pointsList.size(); i++) {
            points[i] = pointsList.get(i);
        }
        return points;
    }

    public static float[] toChartPoints(ArrayList<LocationPoint> locationPoints, float windowHeight) {
        return toChartPoints(locationPoints, 1, 1, 0, windowHeight);
    }
}
