package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.DriverDAO;
import com.osrtc.bustracking.model.Driver;
import java.util.List;

/**
 * Service layer for Driver operations.
 * Handles business logic and validation before calling the DAO layer.
 */
public class DriverService {

    // DAO instance to interact with the database
    private DriverDAO driverDAO = new DriverDAO();

    /**
     * Retrieve all drivers from the database.
     * @return List of Driver objects
     */
    public List<Driver> getAllDrivers() {
        return driverDAO.getAllDrivers();
    }

    /**
     * Retrieve a specific driver by ID.
     * @param id Driver ID
     * @return Driver object if found
     */
    public Driver getDriverById(int id) {
        return driverDAO.getDriverById(id);
    }

    /**
     * Add a new driver.
     * Performs validation to ensure required fields are provided.
     * @param driver Driver object to add
     * @return true if added successfully
     * @throws IllegalArgumentException if required fields are missing
     */
    public boolean addDriver(Driver driver) {
        if(driver.getDriverName() == null || driver.getLicenseNumber() == null) {
            throw new IllegalArgumentException("Driver Name and License Number are required.");
        }
        return driverDAO.addDriver(driver);
    }

    /**
     * Add multiple drivers in bulk.
     * Each driver is validated before insertion.
     * Returns a list of status messages for each driver.
     * @param drivers List of Driver objects to add
     * @return List of messages indicating success/failure
     */
    public List<String> addMultipleDrivers(List<Driver> drivers) {
        List<String> responses = new java.util.ArrayList<>();

        for (Driver driver : drivers) {
            try {
                // Validate each driver
                if(driver.getDriverName() == null && driver.getLicenseNumber() == null) {
                    throw new IllegalArgumentException("Driver Name and License Number are required.");
                }
                driverDAO.addDriver(driver);
                responses.add("Driver Added: " + driver.getDriverName());
            } catch (Exception e) {
                responses.add("Failed: " + driver.getDriverName() + " | Reason: " + e.getMessage());
            }
        }
        return responses;
    }

    /**
     * Update an existing driver's details.
     * Checks if the driver exists first.
     * @param driver Driver object with updated details
     * @return true if update successful
     * @throws IllegalArgumentException if driver does not exist
     */
    public boolean updateDriver(Driver driver) {
        if(driverDAO.getDriverById(driver.getDriverId()) == null) {
            throw new IllegalArgumentException("Driver not found.");
        }
        return driverDAO.updateDriver(driver);
    }

    /**
     * Update only the status of a driver (e.g., Active, Inactive).
     * Checks if the driver exists first.
     * @param driver Driver object with updated status
     * @return true if update successful
     * @throws IllegalArgumentException if driver does not exist
     */
    public boolean updateDriverStatus(Driver driver) {
        if(driverDAO.getDriverById(driver.getDriverId()) == null) {
            throw new IllegalArgumentException("Driver not found.");
        }
        return driverDAO.updateDriverStatus(driver);
    }

    /**
     * Bulk update multiple drivers.
     * Checks if each driver exists, then updates.
     * Throws exception if a driver does not exist or update fails.
     * @param drivers List of Driver objects to update
     * @return List of updated Driver objects
     * @throws Exception if any driver does not exist or update fails
     */
    public List<Driver> bulkUpdateDrivers(List<Driver> drivers) throws Exception {
        List<Driver> updated = new java.util.ArrayList<>();
        for (Driver driver : drivers) {
            if (driverDAO.getDriverById(driver.getDriverId()) == null) {
                throw new IllegalArgumentException("Driver with ID " + driver.getDriverId() + " not found.");
            }
            boolean success = driverDAO.updateDriver(driver);
            if (success) {
                updated.add(driver);
            } else {
                throw new Exception("Failed to update driver with ID " + driver.getDriverId());
            }
        }
        return updated;
    }

    /**
     * Delete a driver by ID.
     * Checks if the driver exists first.
     * @param id Driver ID
     * @return true if deletion successful
     * @throws IllegalArgumentException if driver does not exist
     */
    public boolean deleteDriver(int id) {
        if(driverDAO.getDriverById(id) == null) {
            throw new IllegalArgumentException("Driver not found.");
        }
        return driverDAO.deleteDriver(id);
    }
}
