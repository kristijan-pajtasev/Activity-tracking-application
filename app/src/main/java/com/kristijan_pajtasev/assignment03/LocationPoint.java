package com.kristijan_pajtasev.assignment03;

public class LocationPoint {
    private float altitude, latitude, longitude;
    private long time;

    public LocationPoint(float altitude, float latitude, float longitude, long time) {
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }
}
