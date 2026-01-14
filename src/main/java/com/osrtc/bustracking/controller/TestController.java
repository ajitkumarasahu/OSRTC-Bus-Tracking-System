// Package declaration: groups related classes together
package com.osrtc.bustracking.controller;

// Import statements for necessary classes and annotations
import javax.ws.rs.GET;                       // Marks a method as responding to HTTP GET requests
import javax.ws.rs.Path;                      // Defines the URL path for this controller/class
import javax.ws.rs.Produces;                  // Defines the type of response the method produces
import javax.ws.rs.core.MediaType;            // Provides constants for media types (e.g., JSON)
import java.time.LocalDateTime;               // Represents current date and time
import java.time.format.DateTimeFormatter;    // Formats date and time into a string

// ================== CLASS DEFINITION ==================
@Path("/test")  // URL path for this controller. Full endpoint = /api/test (based on web.xml mapping)
public class TestController {

    // ================== METHOD DEFINITION ==================
    @GET  // This method will respond to HTTP GET requests
    @Produces(MediaType.APPLICATION_JSON)  // The response content type is JSON
    public String testAPI() {

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define the format for the date and time (day-month-year hour:minute:second)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Build and return a JSON string as a response
        // Fields:
        //   status -> indicates success of the API call
        //   message -> confirmation that controller layer is working
        //   serverTime -> current server time
        return "{" 
                + "\"status\":\"SUCCESS\","
                + "\"message\":\"Controller Layer Working Successfully\","
                + "\"serverTime\":\"" + now.format(formatter) + "\""
                + "}";              
    }
}


