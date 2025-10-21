package com.osrtc.bustracking.model;

import java.util.Date;

public class Location {
    private int locationId;
    private int busId;
    private double latitude;
    private double longitude;
    private Date timestamp;

    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
