package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.LocationDAO;
import com.osrtc.bustracking.dao.BusDAO;
import com.osrtc.bustracking.model.Location;
import java.util.List;

/**
 * Service layer for Location operations.
 * Handles validation and business logic before interacting with the DAO.
 */
public class LocationService {

    // DAO instance to perform Location-related database operations
    private LocationDAO locationDAO = new LocationDAO();

    // DAO instance to check if the bus exists before adding/fetching locations
    private BusDAO busDAO = new BusDAO();

    /**
     * Retrieve all locations from the database.
     * @return List of all Location objects
     */
    public List<Location> getAllLocations() {
        return locationDAO.getAllLocations();
    }

    /**
     * Retrieve all locations for a specific bus.
     * Checks if the bus exists before fetching locations.
     * @param busId ID of the bus
     * @return List of Location objects for the given bus
     * @throws IllegalArgumentException if the bus does not exist
     */
    public List<Location> getLocationsByBus(int busId) {
        // Validate that the bus exists
        if(busDAO.getBusById(busId) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        // Fetch locations for the bus
        return locationDAO.getLocationsByBusId(busId);
    }

    /**
     * Retrieve the latest location of a specific bus.
     * Checks if the bus exists first.
     * @param busId ID of the bus
     * @return Latest Location object for the bus
     * @throws IllegalArgumentException if the bus does not exist
     */
    public Location getLatestLocation(int busId) {
        // Validate that the bus exists
        if(busDAO.getBusById(busId) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        // Fetch the latest location for the bus
        return locationDAO.getLatestLocationByBusId(busId);
    }

    /**
     * Add a new location for a bus.
     * Ensures the bus exists before inserting the location.
     * @param location Location object to add
     * @return true if the location was added successfully
     * @throws IllegalArgumentException if the bus does not exist
     */
    public boolean addLocation(Location location) {
        // Validate that the bus exists
        if(busDAO.getBusById(location.getBusId()) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        // Add the location to the database
        return locationDAO.addLocation(location);
    }
}
