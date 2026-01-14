package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.*;
import com.osrtc.bustracking.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for Bus operations.
 * Handles business logic and validation before delegating to the DAO layer.
 */
public class BusService {

    // DAO instances to interact with the database for Bus, Route, and Driver
    private BusDAO busDAO = new BusDAO();
    private RouteDAO routeDAO = new RouteDAO();
    private DriverDAO driverDAO = new DriverDAO();
    BusDAO dao = new BusDAO(); // Seems redundant, could remove

    /**
     * Retrieve all buses from the database.
     * @return List of Bus objects
     */
    public List<Bus> getAllBuses() {
        return busDAO.getAllBuses();
    }

    /**
     * Retrieve a specific Bus by its ID.
     * @param id Bus ID
     * @return Bus object if found
     */
    public Bus getBusById(int id) {
        return busDAO.getBusById(id);
    }

    /**
     * Add a new Bus to the system.
     * Performs validations:
     *  - Bus number must not be null
     *  - Route ID and Driver ID must be valid
     *  - Prevent duplicate bus numbers
     * @param bus Bus object to add
     * @return true if added successfully
     * @throws IllegalArgumentException if validation fails
     */
    public boolean addBus(Bus bus) {
        // Basic null/invalid checks
        if(bus.getBusNumber() == null || bus.getRouteId() <= 0 || bus.getDriverId() <= 0) {
            throw new IllegalArgumentException("Bus Number, Route, and Driver are required.");
        }
        // Check if Route exists
        if(routeDAO.getRouteById(bus.getRouteId()) == null) {
            throw new IllegalArgumentException("Route does not exist.");
        }
        // Check if Driver exists
        if(driverDAO.getDriverById(bus.getDriverId()) == null) {
            throw new IllegalArgumentException("Driver does not exist.");
        }
        // Prevent duplicate bus numbers
        for(Bus b : busDAO.getAllBuses()) {
            if(b.getBusNumber().equalsIgnoreCase(bus.getBusNumber())) {
                throw new IllegalArgumentException("Bus with same number already exists.");
            }
        }
        return busDAO.addBus(bus);
    }

    /**
     * Bulk insert multiple buses.
     * Each bus is validated before insertion.
     * Returns a list of messages indicating success/failure for each bus.
     * @param buses List of Bus objects to add
     * @return List of status messages
     */
    public List<String> multipleBusInsert(List<Bus> buses) {
        List<String> responses = new ArrayList<>();

        for (Bus bus : buses) {
            try {
                validateBus(bus); // Check bus validity
                busDAO.addBus(bus);
                responses.add("Bus Added: " + bus.getBusNumber());
            } catch (Exception e) {
                responses.add("Failed: " + bus.getBusNumber() + " | Reason: " + e.getMessage());
            }
        }

        return responses;
    }

    /**
     * Validates a single Bus object.
     * Checks that bus number is present and route/driver exist.
     * @param bus Bus to validate
     */
    private void validateBus(Bus bus) {
        if (bus.getBusNumber() == null || bus.getBusNumber().isEmpty())
            throw new IllegalArgumentException("Bus Number required");

        if (routeDAO.getRouteById(bus.getRouteId()) == null)
            throw new IllegalArgumentException("Invalid route: " + bus.getRouteId());

        if (driverDAO.getDriverById(bus.getDriverId()) == null)
            throw new IllegalArgumentException("Invalid driver: " + bus.getDriverId());
    }

    /**
     * Update an existing bus.
     * Throws exception if bus does not exist.
     * @param bus Bus object with updated details
     * @return true if update successful
     */
    public boolean updateBus(Bus bus) {
        if(busDAO.getBusById(bus.getBusId()) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        return busDAO.updateBus(bus);
    }

    /**
     * Bulk update multiple buses.
     * Throws exception if any bus ID is invalid.
     * @param buses List of buses to update
     * @return List of updated Bus objects
     * @throws Exception if any bus ID does not exist
     */
    public List<Bus> bulkUpdateBuses(List<Bus> buses) throws Exception {
        List<Bus> updated = new ArrayList<>();
        
        for (Bus b : buses) {
            Bus existing = busDAO.getBusById(b.getBusId());
            if (existing == null) {
                throw new Exception("Bus ID not found: " + b.getBusId());
            }
            busDAO.updateBus(b);
            updated.add(b);
        }
        return updated;
    }

    /**
     * Delete a single bus by ID.
     * @param busId Bus ID
     * @return true if deleted successfully
     */
    public boolean deleteBus(int busId) {
        if(busDAO.getBusById(busId) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        return busDAO.deleteBus(busId);
    }

    /**
     * Bulk delete multiple buses.
     * @param busIds List of bus IDs to delete
     * @return List of deleted Bus objects
     * @throws Exception if any bus ID does not exist
     */
    public List<Bus> bulkDeleteBuses(List<Integer> busIds) throws Exception {
        List<Bus> deleted = new ArrayList<>();
        
        for (Integer id : busIds) {
            Bus existing = busDAO.getBusById(id);
            if (existing == null) {
                throw new Exception("Bus ID not found: " + id);
            }
            busDAO.deleteBus(id);
            deleted.add(existing);
        }
        return deleted;
    }

    /**
     * Assign a driver to a bus.
     * Checks that both bus and driver exist.
     * @param busId Bus ID
     * @param driverId Driver ID
     * @return true if assignment successful
     */
    public boolean assignDriverToBus(int busId, int driverId) {
        Bus bus = busDAO.getBusById(busId);
        if (bus == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        if (driverDAO.getDriverById(driverId) == null) {
            throw new IllegalArgumentException("Driver not found.");
        }
        bus.setDriverId(driverId);
        return busDAO.updateBus(bus);
    }

    /**
     * Update the status of a bus (e.g., Active, Inactive).
     * @param busId Bus ID
     * @param status New status
     * @return true if update successful
     */
    public boolean updateBusStatus(int busId, String status) {
        Bus bus = busDAO.getBusById(busId);
        if (bus == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        bus.setStatus(status);
        return busDAO.updateBus(bus);
    }

}
