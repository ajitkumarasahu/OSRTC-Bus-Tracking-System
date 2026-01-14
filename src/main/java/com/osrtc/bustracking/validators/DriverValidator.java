package com.osrtc.bustracking.validators;

import com.osrtc.bustracking.dao.DriverDAO;
import com.osrtc.bustracking.model.Driver;

/**
 * Validator class to ensure driver license numbers are unique.
 */
public class DriverValidator {

    // DAO to access the driver table in the database
    private DriverDAO driverDAO = new DriverDAO();

    /**
     * Validates that a driver license number is not already present in the database.
     * 
     * @param licenseNumber The license number to validate
     * @throws Exception If a driver with the same license number exists
     */
    public void validateDuplicateLicense(int licenseNumber) throws Exception {
        // ISSUE: This checks by driver ID, not license number
        Driver existing = driverDAO.getDriverById(licenseNumber);

        // If a driver exists with this ID, throw an exception
        if (existing != null) {
            throw new Exception("Driver with license number " + licenseNumber + " already exists");
        }
    }
}
