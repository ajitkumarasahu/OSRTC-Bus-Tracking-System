package com.osrtc.bustracking.model;

/**
 * Represents a Bus entity in the OSRTC Bus Tracking System.
 * Stores bus details including its route, driver, and operational status.
 */
public class Bus {

    /** Unique identifier for the bus */
    private int busId;

    /** Bus number or code for identification */
    private String busNumber;

    /** Associated route ID */
    private int routeId;

    /** Assigned driver ID */
    private int driverId;

    /** Current operational status of the bus (e.g., active, inactive) */
    private String status;

    /**
     * Gets the unique bus ID.
     * @return busId
     */
    public int getBusId() { 
        return busId; 
    }

    /**
     * Sets the unique bus ID.
     * @param busId the ID to set
     */
    public void setBusId(int busId) { 
        this.busId = busId; 
    }

    /**
     * Gets the bus number.
     * @return busNumber
     */
    public String getBusNumber() { 
        return busNumber; 
    }

    /**
     * Sets the bus number.
     * @param busNumber the number to set
     */
    public void setBusNumber(String busNumber) { 
        this.busNumber = busNumber; 
    }

    /**
     * Gets the associated route ID.
     * @return routeId
     */
    public int getRouteId() { 
        return routeId; 
    }

    /**
     * Sets the associated route ID.
     * @param routeId the route ID to set
     */
    public void setRouteId(int routeId) { 
        this.routeId = routeId; 
    }

    /**
     * Gets the assigned driver ID.
     * @return driverId
     */
    public int getDriverId() { 
        return driverId; 
    }

    /**
     * Sets the assigned driver ID.
     * @param driverId the driver ID to set
     */
    public void setDriverId(int driverId) { 
        this.driverId = driverId; 
    }

    /**
     * Gets the current status of the bus.
     * @return status
     */
    public String getStatus() { 
        return status; 
    }

    /**
     * Sets the current status of the bus.
     * @param status the status to set
     */
    public void setStatus(String status) { 
        this.status = status; 
    }
}
