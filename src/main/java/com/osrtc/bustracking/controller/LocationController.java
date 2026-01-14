package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.LocationDAO;
import com.osrtc.bustracking.model.Location;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * REST Controller for Location-related operations.
 * Handles GPS/location tracking data for buses.
 *
 * Base URL: /api/location
 */
@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationController {

    // DAO instance for database operations related to locations
    private LocationDAO dao = new LocationDAO();

    /**
     * Fetch all location records.
     * HTTP GET: /api/location
     */
    @GET
    public List<Location> getAll() {
        return dao.getAllLocations();
    }

    /**
     * Fetch all locations for a specific bus.
     * HTTP GET: /api/location/bus/{busId}
     */
    @GET
    @Path("/bus/{busId}")
    public List<Location> getByBus(@PathParam("busId") int busId) {
        return dao.getLocationsByBusId(busId);
    }

    /**
     * Fetch the latest location for a specific bus.
     * HTTP GET: /api/location/bus/{busId}/latest
     */
    @GET
    @Path("/bus/{busId}/latest")
    public Response getLatestByBus(@PathParam("busId") int busId) {

        Location l = dao.getLatestLocationByBusId(busId);

        // Return 404 if no location data is found
        if (l == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(l).build();
    }

    /**
     * Add a new location entry.
     * HTTP POST: /api/location
     */
    @POST
    public Response add(Location loc, @Context UriInfo ui) {

        if (dao.addLocation(loc)) {

            // Build URI for the newly created location resource
            UriBuilder b = ui.getAbsolutePathBuilder()
                             .path(Integer.toString(loc.getLocationId()));

            return Response.created(b.build())
                           .entity(loc)
                           .build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
