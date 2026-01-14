package com.osrtc.bustracking.dao;

import com.osrtc.bustracking.model.Route;
import com.osrtc.bustracking.util.DBConnection;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object for Route entities.
 * Handles CRUD operations for the "route" table, including bulk insert and bulk update.
 */
public class RouteDAO {

    /**
     * Retrieve all routes from the database.
     * @return List of Route objects
     */
    public List<Route> getAllRoutes() {
        List<Route> list = new ArrayList<>();
        String sql = "SELECT * FROM route";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Route r = new Route();
                r.setRouteId(rs.getInt("route_id"));
                r.setStartPoint(rs.getString("start_point"));
                r.setEndPoint(rs.getString("end_point"));
                r.setTotalDistanceKm(rs.getDouble("total_distance_km"));
                r.setEstimatedTime(rs.getString("estimated_time"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieve a route by its ID.
     * @param id Route ID
     * @return Route object or null if not found
     */
    public Route getRouteById(int id) {
        Route r = null;
        String sql = "SELECT * FROM route WHERE route_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r = new Route();
                    r.setRouteId(rs.getInt("route_id"));
                    r.setStartPoint(rs.getString("start_point"));
                    r.setEndPoint(rs.getString("end_point"));
                    r.setTotalDistanceKm(rs.getDouble("total_distance_km"));
                    r.setEstimatedTime(rs.getString("estimated_time"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * Insert a single route into the database.
     * @param r Route object
     * @return true if insertion succeeded
     */
    public boolean addRoute(Route r) {
        String sql = "INSERT INTO route(start_point, end_point, total_distance_km, estimated_time) VALUES(?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getStartPoint());
            ps.setString(2, r.getEndPoint());
            ps.setDouble(3, r.getTotalDistanceKm());
            ps.setString(4, r.getEstimatedTime());

            int affected = ps.executeUpdate();

            // Retrieve generated route ID
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) r.setRouteId(keys.getInt(1));
                }
            }

            return affected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Insert multiple routes in batch.
     * @param routes List of Route objects
     * @return true if all inserts succeeded
     */
    public boolean addMultipleRoutes(List<Route> routes) {
        String sql = "INSERT INTO route(start_point, end_point, total_distance_km, estimated_time) VALUES(?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Route r : routes) {
                ps.setString(1, r.getStartPoint());
                ps.setString(2, r.getEndPoint());
                ps.setDouble(3, r.getTotalDistanceKm());
                ps.setString(4, r.getEstimatedTime());
                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            for (int res : results) {
                if (res == Statement.EXECUTE_FAILED) return false; // If any insert failed
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update a route by its ID.
     * @param r Route object with updated data
     * @return true if update succeeded
     */
    public boolean updateRoute(Route r) {
        String sql = "UPDATE route SET start_point=?, end_point=?, total_distance_km=?, estimated_time=? WHERE route_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getStartPoint());
            ps.setString(2, r.getEndPoint());
            ps.setDouble(3, r.getTotalDistanceKm());
            ps.setString(4, r.getEstimatedTime());
            ps.setInt(5, r.getRouteId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update multiple routes in batch.
     * @param routes List of Route objects to update
     * @return List of Route objects that failed to update
     */
    public List<Route> bulkUpdateRoutes(List<Route> routes) {
        List<Route> failedUpdates = new ArrayList<>();
        String sql = "UPDATE route SET start_point=?, end_point=?, total_distance_km=?, estimated_time=? WHERE route_id?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Route r : routes) {
                ps.setString(1, r.getStartPoint());
                ps.setString(2, r.getEndPoint());
                ps.setDouble(3, r.getTotalDistanceKm());
                ps.setString(4, r.getEstimatedTime());
                ps.setInt(5, r.getRouteId());

                int affected = ps.executeUpdate();
                if (affected == 0) failedUpdates.add(r); // Track failed updates
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return failedUpdates;
    }

    /**
     * Delete a route by its ID.
     * @param id Route ID
     * @return true if deletion succeeded
     */
    public boolean deleteRoute(int id) {
        String sql = "DELETE FROM route WHERE route_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete multiple routes in batch.
     * @param routeIds List of Route IDs
     * @return true if all deletions succeeded
     */
    public boolean deleteMultipleRoutes(List<Integer> routeIds) {
        String sql = "DELETE FROM route WHERE route_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Integer id : routeIds) {
                ps.setInt(1, id);
                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            for (int r : results) {
                if (r == 0) return false; // If any deletion failed
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
