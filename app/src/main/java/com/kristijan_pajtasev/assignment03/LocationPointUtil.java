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
        double x1 = point1.getAltitude() * Math.cos(point1.getLatitude()) * Math.sin(point1.getLongitude());
        double y1 = point1.getAltitude() * Math.sin(point1.getLatitude());
        double z1 = point1.getAltitude() * Math.cos(point1.getLatitude()) * Math.cos(point1.getLongitude());

        double x2 = point2.getAltitude() * Math.cos(point2.getLatitude()) * Math.sin(point2.getLongitude());
        double y2 = point2.getAltitude() * Math.sin(point2.getLatitude());
        double z2 = point2.getAltitude() * Math.cos(point2.getLatitude()) * Math.cos(point2.getLongitude());

        return Math.sqrt(
                Math.pow(x2 - x1, 2) +
                        Math.pow(y2 - y1, 2) +
                        Math.pow(z2 - z1, 2)
        );
    }

    public static float[] toChartPoints(ArrayList<LocationPoint> locationPoints, float scalarX, float scalarY, float yCorrection) {
        ArrayList<Float> pointsList = new ArrayList<>();
        for(int i = 0; i < locationPoints.size(); i++) {
            LocationPoint point = locationPoints.get(i);
            pointsList.add(i * scalarX);
            pointsList.add((float)(point.getAltitude()- yCorrection) * scalarY);
            if(i>0 && i < locationPoints.size() - 1) {
                pointsList.add(i * scalarX);
                pointsList.add((float)(point.getAltitude()- yCorrection) * scalarY);
            }
        }
        float[] points = new float[pointsList.size()];
        for(int i = 0; i < pointsList.size(); i++) {
            points[i] = pointsList.get(i);
        }
        return points;
    }

    public static float[] toChartPoints(ArrayList<LocationPoint> locationPoints) {
        return toChartPoints(locationPoints, 1, 1, 0);
    }
}
