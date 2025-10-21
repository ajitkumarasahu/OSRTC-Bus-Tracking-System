package com.osrtc.bustracking.util;

import com.osrtc.bustracking.dao.LocationDAO;
import com.osrtc.bustracking.model.Location;
import java.util.*;
import java.util.concurrent.*;

public class GPSSimulator {

    private static final LocationDAO locationDAO = new LocationDAO();
    private static final Random random = new Random();
    private static final int UPDATE_INTERVAL_SECONDS = 5; // Update every 5 seconds

    // Example bus IDs to simulate
    private static final List<Integer> busIds = Arrays.asList(1, 2, 3);

    // Starting coordinates for each bus (latitude, longitude)
    private static final Map<Integer, double[]> busCoordinates = new HashMap<>();

    static {
        busCoordinates.put(1, new double[]{20.2961, 85.8245}); // Bus 1
        busCoordinates.put(2, new double[]{20.2950, 85.8240}); // Bus 2
        busCoordinates.put(3, new double[]{20.2970, 85.8250}); // Bus 3
    }

    public static void startSimulation() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(GPSSimulator::updateLocations, 0, UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    private static void updateLocations() {
        for (Integer busId : busIds) {
            double[] coords = busCoordinates.get(busId);

            // Randomly move bus a little
            coords[0] += (random.nextDouble() - 0.5) * 0.001;
            coords[1] += (random.nextDouble() - 0.5) * 0.001;

            Location loc = new Location();
            loc.setBusId(busId);
            loc.setLatitude(coords[0]);
            loc.setLongitude(coords[1]);
            loc.setTimestamp(new Date());

            locationDAO.addLocation(loc);

            System.out.println("Updated location for Bus " + busId + " -> Lat: " + coords[0] + ", Lng: " + coords[1]);
        }
    }
}
