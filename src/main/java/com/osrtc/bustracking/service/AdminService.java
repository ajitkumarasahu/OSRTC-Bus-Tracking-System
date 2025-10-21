package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.AdminDAO;
import com.osrtc.bustracking.model.Admin;
import java.util.List;

public class AdminService {
    private AdminDAO adminDAO = new AdminDAO();

    public List<Admin> getAllAdmins() {
        return adminDAO.getAllAdmins();
    }

    public Admin getAdminById(int id) {
        return adminDAO.getAdminById(id);
    }

    public boolean addAdmin(Admin admin) {
        if(admin.getUsername() == null || admin.getPassword() == null) {
            throw new IllegalArgumentException("Username and Password are required.");
        }
        return adminDAO.addAdmin(admin);
    }

    public boolean updateAdmin(Admin admin) {
        if(adminDAO.getAdminById(admin.getAdminId()) == null) {
            throw new IllegalArgumentException("Admin not found.");
        }
        return adminDAO.updateAdmin(admin);
    }

    public boolean deleteAdmin(int adminId) {
        if(adminDAO.getAdminById(adminId) == null) {
            throw new IllegalArgumentException("Admin not found.");
        }
        return adminDAO.deleteAdmin(adminId);
    }

    public Admin login(String username, String password) {
        Admin admin = adminDAO.getAdminByUsername(username);
        if(admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        throw new IllegalArgumentException("Invalid username or password.");
    }
}
