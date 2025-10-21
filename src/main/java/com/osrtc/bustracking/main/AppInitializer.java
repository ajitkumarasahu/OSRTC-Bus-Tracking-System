package com.osrtc.bustracking.main;

import com.osrtc.bustracking.util.GPSSimulator;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class AppInitializer extends Application {

    public AppInitializer() {
        // Start GPS simulation when server starts
        GPSSimulator.startSimulation();
    }
}
