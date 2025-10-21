package com.osrtc.bustracking.model;

public class FullBusData {
    private Bus bus;
    private Route route;
    private Driver driver;
    private Location location;
    private Admin admin;

    // Getters and setters
    public Bus getBus() { return bus; }
    public void setBus(Bus bus) { this.bus = bus; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }
}
