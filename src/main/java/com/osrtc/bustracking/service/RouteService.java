package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.RouteDAO;
import com.osrtc.bustracking.model.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for Route operations.
 * Handles business logic and validation before delegating to RouteDAO.
 */
public class RouteService {

    // DAO instance for performing Route database operations
    private RouteDAO routeDAO = new RouteDAO();

    /**
     * Retrieve all routes from the database.
     * @return List of Route objects
     */
    public List<Route> getAllRoutes() {
        return routeDAO.getAllRoutes();
    }

    /**
     * Retrieve a specific route by its ID.
     * @param id Route ID
     * @return Route object
     */
    public Route getRouteById(int id) {
        return routeDAO.getRouteById(id);
    }

    /**
     * Add a new route with validation.
     * Ensures start and end points are not null and prevents duplicate routes.
     * @param route Route object to add
     * @return true if added successfully
     * @throws IllegalArgumentException if validation fails
     */
    public boolean addRoute(Route route) {
        // Validate that start and end points are provided
        if(route.getStartPoint() == null || route.getEndPoint() == null) {
            throw new IllegalArgumentException("Start and End points are required.");
        }

        // Optional: Prevent duplicate routes (same start and end)
        for(Route r : routeDAO.getAllRoutes()) {
            if(r.getStartPoint().equalsIgnoreCase(route.getStartPoint()) &&
               r.getEndPoint().equalsIgnoreCase(route.getEndPoint())) {
                throw new IllegalArgumentException("Route already exists.");
            }
        }

        // Add route using DAO
        return routeDAO.addRoute(route);
    }

    /**
     * Add multiple routes in bulk.
     * Tries to add each route individually and continues on failure.
     * @param routes List of Route objects
     * @return true if all routes added successfully, false if any failed
     */
    public boolean addMultipleRoutes(List<Route> routes) {
        boolean allSuccessful = true;

        for (Route route : routes) {
            try {
                addRoute(route); // Reuse single route validation logic
            } catch (Exception e) {
                allSuccessful = false;
                // Optionally log failure for this specific route
            }
        }

        return allSuccessful;
    }

    /**
     * Update an existing route.
     * Checks if the route exists before updating.
     * @param route Route object with updated data
     * @return true if updated successfully
     * @throws IllegalArgumentException if the route does not exist
     */
    public boolean updateRoute(Route route) {
        if(routeDAO.getRouteById(route.getRouteId()) == null) {
            throw new IllegalArgumentException("Route not found.");
        }
        return routeDAO.updateRoute(route);
    }

    /**
     * Bulk update multiple routes.
     * Validates that each route exists, updates it, and collects updated routes.
     * @param routes List of Route objects to update
     * @return List of successfully updated Route objects
     * @throws IllegalArgumentException if any route does not exist
     */
    public List<Route> bulkUpdateRoutes(List<Route> routes) {
        List<Route> updatedRoutes = new ArrayList<>();

        for (Route route : routes) {
            Route existing = routeDAO.getRouteById(route.getRouteId());
            if (existing == null) {
                throw new IllegalArgumentException("Route not found: ID " + route.getRouteId());
            }
            routeDAO.updateRoute(route); // Update the route
            updatedRoutes.add(route);    // Add to result list
        }

        return updatedRoutes;
    }

    /**
     * Delete a route by its ID.
     * @param routeId ID of the route to delete
     * @return true if deleted successfully
     * @throws IllegalArgumentException if the route does not exist
     */
    public boolean deleteRoute(int routeId) {
        if(routeDAO.getRouteById(routeId) == null) {
            throw new IllegalArgumentException("Route not found.");
        }
        return routeDAO.deleteRoute(routeId);
    }

    /**
     * Bulk delete multiple routes.
     * Validates each route exists before deleting and collects deleted routes.
     * @param routeIds List of route IDs to delete
     * @return List of deleted Route objects
     * @throws Exception if any route ID does not exist
     */
    public List<Route> bulkDeleteRoutes(List<Integer> routeIds) throws Exception {
        List<Route> deletedRoutes = new ArrayList<>();

        for (Integer id : routeIds) {
            Route existing = routeDAO.getRouteById(id);
            if (existing == null) {
                throw new Exception("Route ID not found: " + id);
            }
            routeDAO.deleteRoute(id);   // Delete the route
            deletedRoutes.add(existing); // Add to deleted list
        }

        return deletedRoutes;
    }
}
