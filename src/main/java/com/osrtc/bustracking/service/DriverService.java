package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.DriverDAO;
import com.osrtc.bustracking.model.Driver;
import java.util.List;

public class DriverService {
    private DriverDAO driverDAO = new DriverDAO();

    public List<Driver> getAllDrivers() {
        return driverDAO.getAllDrivers();
    }

    public Driver getDriverById(int id) {
        return driverDAO.getDriverById(id);
    }

    public boolean addDriver(Driver driver) {
        if(driver.getDriverName() == null || driver.getLicenseNumber() == null) {
            throw new IllegalArgumentException("Driver Name and License Number are required.");
        }
        return driverDAO.addDriver(driver);
    }

    public boolean updateDriver(Driver driver) {
        if(driverDAO.getDriverById(driver.getDriverId()) == null) {
            throw new IllegalArgumentException("Driver not found.");
        }
        return driverDAO.updateDriver(driver);
    }

    public boolean deleteDriver(int id) {
        if(driverDAO.getDriverById(id) == null) {
            throw new IllegalArgumentException("Driver not found.");
        }
        return driverDAO.deleteDriver(id);
    }
}
