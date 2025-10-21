package com.osrtc.bustracking.dao;

import com.osrtc.bustracking.model.Driver;
import com.osrtc.bustracking.util.DBConnection;
import java.sql.*;
import java.util.*;

public class DriverDAO {

    public List<Driver> getAllDrivers() {
        List<Driver> list = new ArrayList<>();
        String sql = "SELECT * FROM driver";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Driver d = new Driver();
                d.setDriverId(rs.getInt("driver_id"));
                d.setDriverName(rs.getString("driver_name"));
                d.setPhone(rs.getString("phone"));
                d.setLicenseNumber(rs.getString("license_number"));
                list.add(d);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Driver getDriverById(int id) {
        Driver d = null;
        String sql = "SELECT * FROM driver WHERE driver_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new Driver();
                    d.setDriverId(rs.getInt("driver_id"));
                    d.setDriverName(rs.getString("driver_name"));
                    d.setPhone(rs.getString("phone"));
                    d.setLicenseNumber(rs.getString("license_number"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return d;
    }

    public boolean addDriver(Driver d) {
        String sql = "INSERT INTO driver(driver_name, phone, license_number) VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getDriverName());
            ps.setString(2, d.getPhone());
            ps.setString(3, d.getLicenseNumber());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) d.setDriverId(keys.getInt(1));
                }
            }
            return affected > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateDriver(Driver d) {
        String sql = "UPDATE driver SET driver_name=?, phone=?, license_number=? WHERE driver_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getDriverName());
            ps.setString(2, d.getPhone());
            ps.setString(3, d.getLicenseNumber());
            ps.setInt(4, d.getDriverId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteDriver(int id) {
        String sql = "DELETE FROM driver WHERE driver_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
