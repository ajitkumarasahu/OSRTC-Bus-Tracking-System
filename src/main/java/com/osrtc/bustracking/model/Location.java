package com.osrtc.bustracking.model;

import java.util.Date;

/**
 * Represents a GPS location record for a bus in the OSRTC Bus Tracking System.
 * Each Location is linked to a specific Bus and contains latitude, longitude, and timestamp information.
 */
public class Location {

    /** Unique identifier for the location record */
    private int locationId;

    /** ID of the bus this location belongs to */
    private int busId;

    /** Latitude coordinate of the bus */
    private double latitude;

    /** Longitude coordinate of the bus */
    private double longitude;

    /** Timestamp when the location was recorded */
    private Date timestamp;

    /**
     * Gets the unique location ID.
     * @return locationId
     */
    public int getLocationId() { 
        return locationId; 
    }

    /**
     * Sets the unique location ID.
     * @param locationId the ID to set
     */
    public void setLocationId(int locationId) { 
        this.locationId = locationId; 
    }

    /**
     * Gets the bus ID associated with this location.
     * @return busId
     */
    public int getBusId() { 
        return busId; 
    }

    /**
     * Sets the bus ID associated with this location.
     * @param busId the bus ID to set
     */
    public void setBusId(int busId) { 
        this.busId = busId; 
    }

    /**
     * Gets the latitude coordinate of the bus.
     * @return latitude
     */
    public double getLatitude() { 
        return latitude; 
    }

    /**
     * Sets the latitude coordinate of the bus.
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) { 
        this.latitude = latitude; 
    }

    /**
     * Gets the longitude coordinate of the bus.
     * @return longitude
     */
    public double getLongitude() { 
        return longitude; 
    }

    /**
     * Sets the longitude coordinate of the bus.
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) { 
        this.longitude = longitude; 
    }

    /**
     * Gets the timestamp when this location was recorded.
     * @return timestamp
     */
    public Date getTimestamp() { 
        return timestamp; 
    }

    /**
     * Sets the timestamp when this location was recorded.
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) { 
        this.timestamp = timestamp; 
    }
}
