package com.osrtc.bustracking.dao;

import com.osrtc.bustracking.model.Bus;
import com.osrtc.bustracking.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BusDAO {
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
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

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
        } catch (Exception e) { e.printStackTrace(); }
        return b;
    }

    public boolean addBus(Bus b) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO bus(bus_number, route_id, driver_id, status) VALUES (?, ?, ?, ?)");
            ps.setString(1, b.getBusNumber());
            ps.setInt(2, b.getRouteId());
            ps.setInt(3, b.getDriverId());
            ps.setString(4, b.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

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
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteBus(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM bus WHERE bus_id=?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // In BusDAO.java
    public boolean addMultipleBuses(List<Bus> busList) {
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
                if (r == 0) return false; // if any insert fails
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
