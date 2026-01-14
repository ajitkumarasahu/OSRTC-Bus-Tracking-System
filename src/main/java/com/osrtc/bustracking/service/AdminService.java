package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.AdminDAO;
import com.osrtc.bustracking.model.Admin;
import java.util.List;

/**
 * Service layer for Admin operations.
 * Acts as an intermediary between controllers and the DAO,
 * providing validation and business logic before database access.
 */
public class AdminService {

    // Instantiate AdminDAO to interact with the database for Admin entities
    private AdminDAO adminDAO = new AdminDAO();

    /**
     * Retrieve all admins from the database.
     * @return List of Admin objects
     */
    public List<Admin> getAllAdmins() {
        // Delegate the call to the DAO layer
        return adminDAO.getAllAdmins();
    }

    /**
     * Retrieve a specific Admin by its ID.
     * @param id the ID of the admin
     * @return Admin object if found, null otherwise
     */
    public Admin getAdminById(int id) {
        // Fetch admin from DAO using the given ID
        return adminDAO.getAdminById(id);
    }

    /**
     * Add a new Admin to the database.
     * Performs basic validation before calling the DAO.
     * @param admin Admin object to add
     * @return true if addition was successful
     * @throws IllegalArgumentException if username or password is missing
     */
    public boolean addAdmin(Admin admin) {
        // Validate that username and password are provided
        if(admin.getUsername() == null || admin.getPassword() == null) {
            throw new IllegalArgumentException("Username and Password are required.");
        }
        // Delegate to DAO to insert admin into the database
        return adminDAO.addAdmin(admin);
    }

    /**
     * Update an existing Admin in the database.
     * Checks if the admin exists before updating.
     * @param admin Admin object with updated information
     * @return true if update was successful
     * @throws IllegalArgumentException if admin does not exist
     */
    public boolean updateAdmin(Admin admin) {
        // Check if admin exists in the database
        if(adminDAO.getAdminById(admin.getAdminId()) == null) {
            throw new IllegalArgumentException("Admin not found.");
        }
        // Delegate to DAO to perform the update
        return adminDAO.updateAdmin(admin);
    }

    /**
     * Delete an Admin from the database by its ID.
     * Checks if the admin exists before deletion.
     * @param adminId ID of the admin to delete
     * @return true if deletion was successful
     * @throws IllegalArgumentException if admin does not exist
     */
    public boolean deleteAdmin(int adminId) {
        // Verify admin exists before deletion
        if(adminDAO.getAdminById(adminId) == null) {
            throw new IllegalArgumentException("Admin not found.");
        }
        // Delegate to DAO to delete admin
        return adminDAO.deleteAdmin(adminId);
    }

    /**
     * Authenticate an Admin using username and password.
     * @param username username of the admin
     * @param password password of the admin
     * @return Admin object if authentication succeeds
     * @throws IllegalArgumentException if credentials are invalid
     */
    public Admin login(String username, String password) {
        // Retrieve admin by username
        Admin admin = adminDAO.getAdminByUsername(username);

        // Check if password matches
        if(admin != null && admin.getPassword().equals(password)) {
            return admin; // Authentication successful
        }

        // Throw exception if username or password is incorrect
        throw new IllegalArgumentException("Invalid username or password.");
    }
}
