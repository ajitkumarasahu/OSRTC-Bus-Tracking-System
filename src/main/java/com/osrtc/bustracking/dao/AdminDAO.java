package com.osrtc.bustracking.dao;

import com.osrtc.bustracking.model.Admin;
import com.osrtc.bustracking.util.DBConnection;
import java.sql.*;
import java.util.*;

public class AdminDAO {

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
                a.setPassword(rs.getString("password"));
                list.add(a);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

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
        } catch (Exception e) { e.printStackTrace(); }
        return a;
    }

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
        } catch (Exception e) { e.printStackTrace(); }
        return a;
    }

    public boolean addAdmin(Admin a) {
        String sql = "INSERT INTO admin(username, password) VALUES(?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) a.setAdminId(keys.getInt(1));
                }
            }
            return affected > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateAdmin(Admin a) {
        String sql = "UPDATE admin SET username=?, password=? WHERE admin_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.setInt(3, a.getAdminId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteAdmin(int id) {
        String sql = "DELETE FROM admin WHERE admin_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
