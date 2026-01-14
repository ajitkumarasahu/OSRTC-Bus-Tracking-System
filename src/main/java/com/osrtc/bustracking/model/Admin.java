package com.osrtc.bustracking.model;

/**
 * Represents an Admin entity in the OSRTC Bus Tracking System.
 * Stores basic authentication and identification details.
 */
public class Admin {

    /** Unique identifier for the admin */
    private int adminId;

    /** Username for login */
    private String username;

    /** Password for login (should be stored securely, e.g., hashed) */
    private String password;

    /** 
     * Gets the admin's unique ID.
     * @return adminId
     */
    public int getAdminId() { 
        return adminId; 
    }

    /**
     * Sets the admin's unique ID.
     * @param adminId the ID to set
     */
    public void setAdminId(int adminId) { 
        this.adminId = adminId; 
    }

    /**
     * Gets the admin's username.
     * @return username
     */
    public String getUsername() { 
        return username; 
    }

    /**
     * Sets the admin's username.
     * @param username the username to set
     */
    public void setUsername(String username) { 
        this.username = username; 
    }

    /**
     * Gets the admin's password.
     * @return password
     */
    public String getPassword() { 
        return password; 
    }

    /**
     * Sets the admin's password.
     * @param password the password to set
     */
    public void setPassword(String password) { 
        this.password = password; 
    }
}
