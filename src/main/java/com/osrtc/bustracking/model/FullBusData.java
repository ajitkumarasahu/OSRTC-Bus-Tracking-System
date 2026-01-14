package com.osrtc.bustracking.model;

/**
 * Aggregates all related entities for a bus in the OSRTC Bus Tracking System.
 * This model is used for operations that require handling multiple linked entities together.
 * Contains Bus, Route, Driver, Location, and Admin information.
 */
public class FullBusData {

    /** The bus entity */
    private Bus bus;

    /** The route entity assigned to the bus */
    private Route route;

    /** The driver assigned to the bus */
    private Driver driver;

    /** The current location of the bus */
    private Location location;

    /** The admin responsible for managing this bus */
    private Admin admin;

    /**
     * Gets the bus entity.
     * @return bus
     */
    public Bus getBus() { 
        return bus; 
    }

    /**
     * Sets the bus entity.
     * @param bus the Bus object to set
     */
    public void setBus(Bus bus) { 
        this.bus = bus; 
    }

    /**
     * Gets the route entity.
     * @return route
     */
    public Route getRoute() { 
        return route; 
    }

    /**
     * Sets the route entity.
     * @param route the Route object to set
     */
    public void setRoute(Route route) { 
        this.route = route; 
    }

    /**
     * Gets the driver entity.
     * @return driver
     */
    public Driver getDriver() { 
        return driver; 
    }

    /**
     * Sets the driver entity.
     * @param driver the Driver object to set
     */
    public void setDriver(Driver driver) { 
        this.driver = driver; 
    }

    /**
     * Gets the location entity.
     * @return location
     */
    public Location getLocation() { 
        return location; 
    }

    /**
     * Sets the location entity.
     * @param location the Location object to set
     */
    public void setLocation(Location location) { 
        this.location = location; 
    }

    /**
     * Gets the admin entity.
     * @return admin
     */
    public Admin getAdmin() { 
        return admin; 
    }

    /**
     * Sets the admin entity.
     * @param admin the Admin object to set
     */
    public void setAdmin(Admin admin) { 
        this.admin = admin; 
    }
}
