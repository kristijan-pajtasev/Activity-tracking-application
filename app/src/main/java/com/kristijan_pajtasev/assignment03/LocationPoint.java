package com.kristijan_pajtasev.assignment03;

public class LocationPoint {
    private double altitude, latitude, longitude;
    private long time;

    public LocationPoint(double altitude, double latitude, double longitude, long time) {
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getTime() {
        return time;
    }
}
