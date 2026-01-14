package com.osrtc.bustracking.validators;

import com.osrtc.bustracking.dao.RouteDAO;
import com.osrtc.bustracking.model.Route;

/**
 * Validator class to check for duplicate routes.
 */
public class RouteValidator {

    // DAO instance to access routes from the database
    private RouteDAO routeDAO = new RouteDAO();

    /**
     * Validates that a route with the same start and end points does not already exist.
     *
     * @param startPoint The starting point of the route
     * @param endPoint   The ending point of the route
     * @throws Exception If a route with the same start and end points already exists
     */
    public void validateDuplicateRoute(String startPoint, String endPoint) throws Exception {
        // Loop through all routes in the database
        for (Route r : routeDAO.getAllRoutes()) {

            // Compare ignoring case to detect duplicates
            if (r.getStartPoint().equalsIgnoreCase(startPoint) &&
                r.getEndPoint().equalsIgnoreCase(endPoint)) {

                // If a duplicate is found, throw an exception with a clear message
                throw new Exception("Route from '" + startPoint + "' to '" + endPoint + "' already exists");
            }
        }
    }
}
