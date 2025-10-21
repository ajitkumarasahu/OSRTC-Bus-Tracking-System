package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.RouteDAO;
import com.osrtc.bustracking.model.Route;
import java.util.List;

public class RouteService {
    private RouteDAO routeDAO = new RouteDAO();

    // Get all routes
    public List<Route> getAllRoutes() {
        return routeDAO.getAllRoutes();
    }

    // Get route by ID
    public Route getRouteById(int id) {
        return routeDAO.getRouteById(id);
    }

    // Add route with validation
    public boolean addRoute(Route route) {
        if(route.getStartPoint() == null || route.getEndPoint() == null) {
            throw new IllegalArgumentException("Start and End points are required.");
        }
        // Optionally, prevent duplicate route
        for(Route r : routeDAO.getAllRoutes()) {
            if(r.getStartPoint().equalsIgnoreCase(route.getStartPoint()) &&
               r.getEndPoint().equalsIgnoreCase(route.getEndPoint())) {
                throw new IllegalArgumentException("Route already exists.");
            }
        }
        return routeDAO.addRoute(route);
    }

    // Update route
    public boolean updateRoute(Route route) {
        if(routeDAO.getRouteById(route.getRouteId()) == null) {
            throw new IllegalArgumentException("Route not found.");
        }
        return routeDAO.updateRoute(route);
    }

    // Delete route
    public boolean deleteRoute(int routeId) {
        if(routeDAO.getRouteById(routeId) == null) {
            throw new IllegalArgumentException("Route not found.");
        }
        return routeDAO.deleteRoute(routeId);
    }
}
