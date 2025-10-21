package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.LocationDAO;
import com.osrtc.bustracking.dao.BusDAO;
import com.osrtc.bustracking.model.Location;
import java.util.List;

public class LocationService {
    private LocationDAO locationDAO = new LocationDAO();
    private BusDAO busDAO = new BusDAO();

    public List<Location> getAllLocations() {
        return locationDAO.getAllLocations();
    }

    public List<Location> getLocationsByBus(int busId) {
        if(busDAO.getBusById(busId) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        return locationDAO.getLocationsByBusId(busId);
    }

    public Location getLatestLocation(int busId) {
        if(busDAO.getBusById(busId) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        return locationDAO.getLatestLocationByBusId(busId);
    }

    public boolean addLocation(Location location) {
        if(busDAO.getBusById(location.getBusId()) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        return locationDAO.addLocation(location);
    }
}
