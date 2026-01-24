// Package declaration: groups related classes together
package com.osrtc.bustracking.controller;

/**
 * TestController
 * -------------------------
 * This REST controller is used to test whether the Controller layer
 * of the application is working correctly.
 *
 * It exposes a GET API that returns:
 *  - Current server date & time
 *  - Current day name (English)
 *  - Current month name in Odia language
 *
 * Response format: JSON
 */

import javax.ws.rs.GET;                       // Annotation for HTTP GET method
import javax.ws.rs.Path;                      // Defines URI path for the controller
import javax.ws.rs.Produces;                  // Specifies response media type
import javax.ws.rs.core.MediaType;            // Contains media type constants

import java.time.LocalDateTime;               // Used to fetch current date & time
import java.time.format.DateTimeFormatter;    // Used to format date & time
import java.time.DayOfWeek;                   // Used to get day name
import java.time.Month;                       // Used to get month enum

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller class
 * URL Mapping: /api/test
 */
@Path("/test")
public class TestController {

    /**
     * testAPI()
     * -------------------------
     * Handles HTTP GET requests.
     *
     * @return JSON string containing:
     *         - status
     *         - message
     *         - serverTime
     *         - dayName
     *         - monthOdia
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String testAPI() {

        // Fetch current server date and time
        LocalDateTime now = LocalDateTime.now();

        // Format date and time as dd-MM-yyyy HH:mm:ss
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        /**
         * Map to store Odia month names
         * Key   -> Month enum
         * Value -> Odia month name
         */
        Map<Month, String> odiaMonths = new HashMap<>();
        odiaMonths.put(Month.JANUARY, "ଜାନୁଆରୀ");
        odiaMonths.put(Month.FEBRUARY, "ଫେବୃଆରୀ");
        odiaMonths.put(Month.MARCH, "ମାର୍ଚ୍ଚ");
        odiaMonths.put(Month.APRIL, "ଏପ୍ରିଲ");
        odiaMonths.put(Month.MAY, "ମେ");
        odiaMonths.put(Month.JUNE, "ଜୁନ");
        odiaMonths.put(Month.JULY, "ଜୁଲାଇ");
        odiaMonths.put(Month.AUGUST, "ଅଗଷ୍ଟ");
        odiaMonths.put(Month.SEPTEMBER, "ସେପ୍ଟେମ୍ବର");
        odiaMonths.put(Month.OCTOBER, "ଅକ୍ଟୋବର");
        odiaMonths.put(Month.NOVEMBER, "ନଭେମ୍ବର");
        odiaMonths.put(Month.DECEMBER, "ଡିସେମ୍ବର");

        // Get current month name in Odia
        String odiaMonthName = odiaMonths.get(now.getMonth());

        // Get current day of the week (MONDAY, TUESDAY, etc.)
        DayOfWeek dayOfWeek = now.getDayOfWeek();

        // Convert day name to readable format (Monday, Tuesday, etc.)
        String dayName =
                dayOfWeek.toString().substring(0, 1) +
                dayOfWeek.toString().substring(1).toLowerCase();

        // Build and return JSON response
        return "{"
                + "\"status\":\"SUCCESS\","
                + "\"message\":\"Controller Layer Working Successfully\","
                + "\"serverTime\":\"" + now.format(formatter) + "\","
                + "\"dayName\":\"" + dayName + "\","
                + "\"monthOdia\":\"" + odiaMonthName + "\""
                + "}";
    }
}


