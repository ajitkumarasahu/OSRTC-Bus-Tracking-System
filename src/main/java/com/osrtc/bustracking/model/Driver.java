package com.osrtc.bustracking.model;

/**
 * Represents a Driver entity in the OSRTC Bus Tracking System.
 * Stores driver details such as name, contact, license, and current status.
 */
public class Driver {

    /** Unique identifier for the driver */
    private int driverId;

    /** Full name of the driver */
    private String driverName;

    /** Contact phone number of the driver */
    private String phone;

    /** Driver's license number */
    private String licenseNumber;

    /** Current status of the driver (e.g., active, inactive) */
    private String status;

    /**
     * Gets the unique driver ID.
     * @return driverId
     */
    public int getDriverId() { 
        return driverId; 
    }

    /**
     * Sets the unique driver ID.
     * @param driverId the ID to set
     */
    public void setDriverId(int driverId) { 
        this.driverId = driverId; 
    }

    /**
     * Gets the driver's full name.
     * @return driverName
     */
    public String getDriverName() { 
        return driverName; 
    }

    /**
     * Sets the driver's full name.
     * @param driverName the name to set
     */
    public void setDriverName(String driverName) { 
        this.driverName = driverName; 
    }

    /**
     * Gets the driver's phone number.
     * @return phone
     */
    public String getPhone() { 
        return phone; 
    }

    /**
     * Sets the driver's phone number.
     * @param phone the phone number to set
     */
    public void setPhone(String phone) { 
        this.phone = phone; 
    }

    /**
     * Gets the driver's license number.
     * @return licenseNumber
     */
    public String getLicenseNumber() { 
        return licenseNumber; 
    }

    /**
     * Sets the driver's license number.
     * @param licenseNumber the license number to set
     */
    public void setLicenseNumber(String licenseNumber) { 
        this.licenseNumber = licenseNumber; 
    }

    /**
     * Gets the current status of the driver.
     * @return status
     */
    public String getStatus() { 
        return status; 
    }

    /**
     * Sets the current status of the driver.
     * @param status the status to set
     */
    public void setStatus(String status) { 
        this.status = status; 
    }
}
