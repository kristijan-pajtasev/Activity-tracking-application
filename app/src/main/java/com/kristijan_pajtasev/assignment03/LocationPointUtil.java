package com.kristijan_pajtasev.assignment03;

import android.location.Location;

import java.util.ArrayList;

/**
 * Utility class containing helper function for getting statistics data
 */
public class LocationPointUtil {
    /**
     * Finds maximum altitude in given list
     * @param locationPoints
     * @return double maximum altitude
     */
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

    /**
     * Finds minimum altitude in list
     * @param locationPoints
     * @return double minimum altitude found
     */
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

    /**
     * Calculates altitude gain in a list
     * @param locationPoints
     * @return double altitude gain
     */
    public static double altitudeGain(ArrayList<LocationPoint> locationPoints) {
        double gain = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            double altCurrent = locationPoints.get(i).getAltitude();
            double altNext = locationPoints.get(i + 1).getAltitude();
            if(altCurrent < altNext) gain += (altNext - altCurrent);
        }
        return gain;
    }

    /**
     * Calculates altitude loss in a list
     * @param locationPoints
     * @return double altitude loss
     */
    public static double altitudeLoss(ArrayList<LocationPoint> locationPoints) {
        double loss = 0;
        for(int i = 0; i < locationPoints.size() - 1; i++) {
            double altCurrent = locationPoints.get(i).getAltitude();
            double altNext = locationPoints.get(i + 1).getAltitude();
            if(altCurrent > altNext) loss += (altCurrent - altNext);
        }
        return loss;
    }

    /**
     * Calculates maximum speed in a list
     * @param locationPoints
     * @return double maximum speed
     */
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

    /**
     * Calculates minimum speed in a list
     * @param locationPoints
     * @return double minimum speed
     */
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

    /**
     * Calculates average speed in a list
     * @param locationPoints
     * @return double average speed
     */
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

    /**
     * Calculates total distance traveled in meters
     * @param locationPoints
     * @return double total distance
     */
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

    /**
     * Calculates distance in meters between two location points
     * @param point1
     * @param point2
     * @return double distance in meters
     */
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

    /**
     * Converts List<LocationPoints> into array of points for chart
     * @param locationPoints
     * @param scalarX
     * @param scalarY
     * @param yCorrection
     * @param windowHeight
     * @return float[] list of values for drawing chart
     */
    public static float[] toChartPoints(ArrayList<LocationPoint> locationPoints, float scalarX, float scalarY, float yCorrection, float windowHeight) {
        ArrayList<Float> pointsList = new ArrayList<>();
        for(int i = 0; i < locationPoints.size(); i++) {
            LocationPoint point = locationPoints.get(i);
            pointsList.add(i * scalarX + 10);
            pointsList.add(windowHeight - (float)(point.getAltitude() - yCorrection) * scalarY - 10);
            if(i>0 && i < locationPoints.size() - 1) {
                pointsList.add(i * scalarX + 10);
                pointsList.add(windowHeight - (float)(point.getAltitude() - yCorrection) * scalarY - 10);
            }
        }
        float[] points = new float[pointsList.size()];
        for(int i = 0; i < pointsList.size(); i++) {
            points[i] = pointsList.get(i);
        }
        return points;
    }

    /**
     * Determines total time of activity in seconds
     * @param locationPoints
     * @return long total time
     */
    public static long totalTime(ArrayList<LocationPoint> locationPoints) {
        LocationPoint pointA = locationPoints.get(0);
        LocationPoint pointB = locationPoints.get(locationPoints.size() - 1);

        return (pointB.getTime() - pointA.getTime()) / 1000;
    }
}
