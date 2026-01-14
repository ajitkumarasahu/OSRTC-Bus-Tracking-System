package com.osrtc.bustracking.dao;

import com.osrtc.bustracking.model.Bus;
import com.osrtc.bustracking.util.DBConnection;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object for Bus entities.
 * Handles CRUD operations and related updates for the "bus" table.
 */
public class BusDAO {

    /**
     * Retrieve all buses from the database.
     * @return List of Bus objects
     */
    public List<Bus> getAllBuses() {
        List<Bus> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM bus");
            while (rs.next()) {
                Bus b = new Bus();
                b.setBusId(rs.getInt("bus_id"));
                b.setBusNumber(rs.getString("bus_number"));
                b.setRouteId(rs.getInt("route_id"));
                b.setDriverId(rs.getInt("driver_id"));
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }

    /**
     * Retrieve a single bus by its ID.
     * @param id Bus ID
     * @return Bus object or null if not found
     */
    public Bus getBusById(int id) {
        Bus b = null;
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM bus WHERE bus_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                b = new Bus();
                b.setBusId(rs.getInt("bus_id"));
                b.setBusNumber(rs.getString("bus_number"));
                b.setRouteId(rs.getInt("route_id"));
                b.setDriverId(rs.getInt("driver_id"));
                b.setStatus(rs.getString("status"));
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return b;
    }

    /**
     * Insert a single bus into the database.
     * @param b Bus object
     * @return true if insertion succeeded
     */
    public boolean addBus(Bus b) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO bus(bus_number, route_id, driver_id, status) VALUES (?, ?, ?, ?)");
            ps.setString(1, b.getBusNumber());
            ps.setInt(2, b.getRouteId());
            ps.setInt(3, b.getDriverId());
            ps.setString(4, b.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }

    /**
     * Insert multiple buses in batch mode.
     * @param busList List of Bus objects
     * @return true if all inserts succeeded
     */
    public boolean multipleBusInsert(List<Bus> busList) {
        String sql = "INSERT INTO bus (bus_number, route_id, driver_id, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Bus b : busList) {
                ps.setString(1, b.getBusNumber());
                ps.setInt(2, b.getRouteId());
                ps.setInt(3, b.getDriverId());
                ps.setString(4, b.getStatus());
                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            for (int r : results) {
                if (r == 0) return false; // any insert failed
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update an existing bus record.
     * @param b Bus object with updated data
     * @return true if update succeeded
     */
    public boolean updateBus(Bus b) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE bus SET bus_number=?, route_id=?, driver_id=?, status=? WHERE bus_id=?");
            ps.setString(1, b.getBusNumber());
            ps.setInt(2, b.getRouteId());
            ps.setInt(3, b.getDriverId());
            ps.setString(4, b.getStatus());
            ps.setInt(5, b.getBusId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }

    /**
     * Bulk update multiple buses.
     * @param buses List of Bus objects to update
     * @return List of buses that failed to update
     */
    public List<Bus> bulkUpdateBuses(List<Bus> buses) {
        List<Bus> failedUpdates = new ArrayList<>();
        String sql = "UPDATE bus SET bus_number=?, route_id=?, driver_id=?, status=? WHERE bus_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Bus b : buses) {
                ps.setString(1, b.getBusNumber());
                ps.setInt(2, b.getRouteId());
                ps.setInt(3, b.getDriverId());
                ps.setString(4, b.getStatus());
                ps.setInt(5, b.getBusId());
                int affected = ps.executeUpdate();
                if (affected == 0) {
                    failedUpdates.add(b); // update failed
                }
            }
            return failedUpdates;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return failedUpdates;
    }

    /**
     * Delete a bus by ID.
     * @param id Bus ID
     * @return true if deletion succeeded
     */
    public boolean deleteBus(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM bus WHERE bus_id=?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }

    /**
     * Delete multiple buses in batch.
     * @param busIds List of bus IDs
     * @return true if all deletes succeeded
     */
    public boolean deleteMultipleBuses(List<Integer> busIds) {
        String sql = "DELETE FROM bus WHERE bus_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Integer id : busIds) {
                ps.setInt(1, id);
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            for (int r : results) {
                if (r == 0) return false; // any delete failed
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update the status of a bus.
     * @param busId Bus ID
     * @param status New status
     * @return true if update succeeded
     */
    public boolean updateBusStatus(int busId, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE bus SET status=? WHERE bus_id=?");
            ps.setString(1, status);
            ps.setInt(2, busId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }

    /**
     * Assign a driver to a bus.
     * @param busId Bus ID
     * @param driverId Driver ID
     * @return true if assignment succeeded
     */
    public boolean assignDriverToBus(int busId, int driverId) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE bus SET driver_id=? WHERE bus_id=?");
            ps.setInt(1, driverId);
            ps.setInt(2, busId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }
}
