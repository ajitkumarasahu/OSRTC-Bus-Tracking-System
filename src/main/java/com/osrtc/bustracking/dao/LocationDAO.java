package com.osrtc.bustracking.dao;

import com.osrtc.bustracking.model.Location;
import com.osrtc.bustracking.util.DBConnection;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object for Location entities.
 * Handles CRUD operations for the "location" table, including retrieval by bus and latest location.
 */
public class LocationDAO {

    /**
     * Insert a new location record for a bus.
     * @param loc Location object containing busId, latitude, and longitude
     * @return true if insertion succeeded
     */
    public boolean addLocation(Location loc) {
        String sql = "INSERT INTO location(bus_id, latitude, longitude) VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, loc.getBusId());
            ps.setDouble(2, loc.getLatitude());
            ps.setDouble(3, loc.getLongitude());

            int affected = ps.executeUpdate();

            // Retrieve auto-generated location ID
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) loc.setLocationId(keys.getInt(1));
                }
            }

            return affected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieve all locations for a specific bus, ordered by timestamp descending.
     * @param busId ID of the bus
     * @return List of Location objects
     */
    public List<Location> getLocationsByBusId(int busId) {
        List<Location> list = new ArrayList<>();
        String sql = "SELECT * FROM location WHERE bus_id=? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, busId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Location l = new Location();
                    l.setLocationId(rs.getInt("location_id"));
                    l.setBusId(rs.getInt("bus_id"));
                    l.setLatitude(rs.getDouble("latitude"));
                    l.setLongitude(rs.getDouble("longitude"));
                    l.setTimestamp(rs.getTimestamp("timestamp"));
                    list.add(l);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Retrieve the latest location for a specific bus.
     * @param busId ID of the bus
     * @return Location object of the latest entry or null if none found
     */
    public Location getLatestLocationByBusId(int busId) {
        Location l = null;
        String sql = "SELECT * FROM location WHERE bus_id=? ORDER BY timestamp DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, busId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    l = new Location();
                    l.setLocationId(rs.getInt("location_id"));
                    l.setBusId(rs.getInt("bus_id"));
                    l.setLatitude(rs.getDouble("latitude"));
                    l.setLongitude(rs.getDouble("longitude"));
                    l.setTimestamp(rs.getTimestamp("timestamp"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return l;
    }

    /**
     * Retrieve all locations for all buses, ordered by timestamp descending.
     * @return List of Location objects
     */
    public List<Location> getAllLocations() {
        List<Location> list = new ArrayList<>();
        String sql = "SELECT * FROM location ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Location l = new Location();
                l.setLocationId(rs.getInt("location_id"));
                l.setBusId(rs.getInt("bus_id"));
                l.setLatitude(rs.getDouble("latitude"));
                l.setLongitude(rs.getDouble("longitude"));
                l.setTimestamp(rs.getTimestamp("timestamp"));
                list.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
