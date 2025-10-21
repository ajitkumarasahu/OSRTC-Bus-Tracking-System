package com.osrtc.bustracking.dao;

import com.osrtc.bustracking.model.Route;
import com.osrtc.bustracking.util.DBConnection;
import java.sql.*;
import java.util.*;

public class RouteDAO {

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
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

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
        } catch (Exception e) { e.printStackTrace(); }
        return r;
    }

    public boolean addRoute(Route r) {
        String sql = "INSERT INTO route(start_point, end_point, total_distance_km, estimated_time) VALUES(?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getStartPoint());
            ps.setString(2, r.getEndPoint());
            ps.setDouble(3, r.getTotalDistanceKm());
            ps.setString(4, r.getEstimatedTime());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) r.setRouteId(keys.getInt(1));
                }
            }
            return affected > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

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
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteRoute(int id) {
        String sql = "DELETE FROM route WHERE route_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
