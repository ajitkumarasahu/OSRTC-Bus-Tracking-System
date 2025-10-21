package com.osrtc.bustracking.service;

import com.osrtc.bustracking.dao.*;
import com.osrtc.bustracking.model.*;

import java.util.List;

public class BusService {
    private BusDAO busDAO = new BusDAO();
    private RouteDAO routeDAO = new RouteDAO();
    private DriverDAO driverDAO = new DriverDAO();
    BusDAO dao = new BusDAO();

    public List<Bus> getAllBuses() {
        return busDAO.getAllBuses();
    }

    public Bus getBusById(int id) {
        return busDAO.getBusById(id);
    }

    public boolean addBus(Bus bus) {
        if(bus.getBusNumber() == null || bus.getRouteId() <= 0 || bus.getDriverId() <= 0) {
            throw new IllegalArgumentException("Bus Number, Route, and Driver are required.");
        }
        if(routeDAO.getRouteById(bus.getRouteId()) == null) {
            throw new IllegalArgumentException("Route does not exist.");
        }
        if(driverDAO.getDriverById(bus.getDriverId()) == null) {
            throw new IllegalArgumentException("Driver does not exist.");
        }
        // prevent duplicate bus numbers
        for(Bus b : busDAO.getAllBuses()) {
            if(b.getBusNumber().equalsIgnoreCase(bus.getBusNumber())) {
                throw new IllegalArgumentException("Bus with same number already exists.");
            }
        }
        return busDAO.addBus(bus);
    }

    public boolean updateBus(Bus bus) {
        if(busDAO.getBusById(bus.getBusId()) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        return busDAO.updateBus(bus);
    }

    public boolean deleteBus(int busId) {
        if(busDAO.getBusById(busId) == null) {
            throw new IllegalArgumentException("Bus not found.");
        }
        return busDAO.deleteBus(busId);
    }

    // //  ✅ Add multiple buses
    // public boolean addMultipleBuses(List<Bus> bus) { 
    //     return busDAO.addMultipleBuses(bus); 
    // }
}
