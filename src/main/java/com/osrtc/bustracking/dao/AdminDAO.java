package com.osrtc.bustracking.dao;

import com.osrtc.bustracking.model.Admin;
import com.osrtc.bustracking.util.DBConnection;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object for Admin entities.
 * Handles CRUD operations on the "admin" table.
 */
public class AdminDAO {

    /**
     * Retrieve all admins from the database.
     * @return List of Admin objects
     */
    public List<Admin> getAllAdmins() {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Admin a = new Admin();
                a.setAdminId(rs.getInt("admin_id"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password")); // Note: passwords stored in plain text
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Retrieve a single admin by ID.
     * @param id Admin ID
     * @return Admin object or null if not found
     */
    public Admin getAdminById(int id) {
        Admin a = null;
        String sql = "SELECT * FROM admin WHERE admin_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    a = new Admin();
                    a.setAdminId(rs.getInt("admin_id"));
                    a.setUsername(rs.getString("username"));
                    a.setPassword(rs.getString("password"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }

    /**
     * Retrieve a single admin by username.
     * @param username Admin username
     * @return Admin object or null if not found
     */
    public Admin getAdminByUsername(String username) {
        Admin a = null;
        String sql = "SELECT * FROM admin WHERE username=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    a = new Admin();
                    a.setAdminId(rs.getInt("admin_id"));
                    a.setUsername(rs.getString("username"));
                    a.setPassword(rs.getString("password"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }

    /**
     * Add a new admin to the database.
     * @param a Admin object to add
     * @return true if insertion succeeded
     */
    public boolean addAdmin(Admin a) {
        String sql = "INSERT INTO admin(username, password) VALUES(?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());

            int affected = ps.executeUpdate();

            // Retrieve generated admin ID
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        a.setAdminId(keys.getInt(1));
                    }
                }
            }

            return affected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Update an existing admin's details.
     * @param a Admin object with updated data
     * @return true if update succeeded
     */
    public boolean updateAdmin(Admin a) {
        String sql = "UPDATE admin SET username=?, password=? WHERE admin_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.setInt(3, a.getAdminId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete an admin by ID.
     * @param id Admin ID
     * @return true if deletion succeeded
     */
    public boolean deleteAdmin(int id) {
        String sql = "DELETE FROM admin WHERE admin_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
