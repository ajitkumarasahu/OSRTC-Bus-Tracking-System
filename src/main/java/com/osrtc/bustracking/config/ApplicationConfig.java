// Package declaration – groups this class under the config package
package com.osrtc.bustracking.config;

// Import Jersey's ResourceConfig class used to configure REST resources
import org.glassfish.jersey.server.ResourceConfig;

// Import annotation to define the base URI for REST APIs
import javax.ws.rs.ApplicationPath;

// Defines the base path for all REST endpoints (e.g., /api/endpoint)
@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {

    // Constructor is called when the application starts
    public ApplicationConfig() {

        // Tells Jersey to scan this package for REST controllers (@Path classes)
        packages("com.osrtc.bustracking.controller");
    }
}
