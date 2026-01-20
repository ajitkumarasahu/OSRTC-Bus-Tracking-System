
package com.osrtc.bustracking.config;

import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        // Scan all REST controllers
        packages("com.osrtc.bustracking.controller");
    }
}
