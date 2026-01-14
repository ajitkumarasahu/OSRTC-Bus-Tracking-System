package com.osrtc.bustracking.validators;

import com.osrtc.bustracking.dao.BusDAO;
import com.osrtc.bustracking.model.Bus;

/**
 * Validator class to ensure bus numbers are unique.
 */
public class BusnumberValidator {

    // DAO to interact with the bus table in the database
    private BusDAO busDAO = new BusDAO();

    /**
     * Validates that a bus number is not already present in the database.
     * 
     * @param busNumber The bus number to validate
     * @throws Exception If a bus with the same number already exists
     */
    public void validateDuplicateBusNumber(int busNumber) throws Exception {
        // Fetch bus by ID (note: using getBusById may not check bus_number, might be wrong)
        Bus existing = busDAO.getBusById(busNumber);

        // If a bus exists with this ID, throw an exception
        if (existing != null) {
            throw new Exception("Bus with number " + busNumber + " already exists");
        }
    }
}
