package com.osrtc.bustracking.model;

/**
 * Represents a bus route in the OSRTC Bus Tracking System.
 * A Route contains information about the start and end points, distance, and estimated travel time.
 */
public class Route {

    /** Unique identifier for the route */
    private int routeId;

    /** Starting point of the route */
    private String startPoint;

    /** Ending point of the route */
    private String endPoint;

    /** Total distance of the route in kilometers */
    private double totalDistanceKm;

    /** Estimated travel time for the route (e.g., "2h 30m") */
    private String estimatedTime;

    /**
     * Gets the unique route ID.
     * @return routeId
     */
    public int getRouteId() { 
        return routeId; 
    }

    /**
     * Sets the unique route ID.
     * @param routeId the route ID to set
     */
    public void setRouteId(int routeId) { 
        this.routeId = routeId; 
    }

    /**
     * Gets the starting point of the route.
     * @return startPoint
     */
    public String getStartPoint() { 
        return startPoint; 
    }

    /**
     * Sets the starting point of the route.
     * @param startPoint the start point to set
     */
    public void setStartPoint(String startPoint) { 
        this.startPoint = startPoint; 
    }

    /**
     * Gets the ending point of the route.
     * @return endPoint
     */
    public String getEndPoint() { 
        return endPoint; 
    }

    /**
     * Sets the ending point of the route.
     * @param endPoint the end point to set
     */
    public void setEndPoint(String endPoint) { 
        this.endPoint = endPoint; 
    }

    /**
     * Gets the total distance of the route in kilometers.
     * @return totalDistanceKm
     */
    public double getTotalDistanceKm() { 
        return totalDistanceKm; 
    }

    /**
     * Sets the total distance of the route in kilometers.
     * @param totalDistanceKm the distance to set
     */
    public void setTotalDistanceKm(double totalDistanceKm) { 
        this.totalDistanceKm = totalDistanceKm; 
    }

    /**
     * Gets the estimated travel time for the route.
     * @return estimatedTime
     */
    public String getEstimatedTime() { 
        return estimatedTime; 
    }

    /**
     * Sets the estimated travel time for the route.
     * @param estimatedTime the estimated time to set
     */
    public void setEstimatedTime(String estimatedTime) { 
        this.estimatedTime = estimatedTime; 
    }
}
