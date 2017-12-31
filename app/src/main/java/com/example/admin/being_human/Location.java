package com.example.admin.being_human;

/**
 * Created by kulsoom on 29-Dec-17.
 */

public class Location{
    double latitude;
    double longitude;
    long googleTimestamp;
    long deviceTimestamp;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getGoogleTimestamp() {
        return googleTimestamp;
    }

    public void setGoogleTimestamp(long googleTimestamp) {
        this.googleTimestamp = googleTimestamp;
    }

    public long getDeviceTimestamp() {
        return deviceTimestamp;
    }

    public void setDeviceTimestamp(long deviceTimestamp) {
        this.deviceTimestamp = deviceTimestamp;
    }
}
