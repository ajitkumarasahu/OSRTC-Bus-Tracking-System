package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.DriverDAO;
import com.osrtc.bustracking.model.Driver;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * REST Controller for Driver-related operations
 * Base URL: /api/driver
 */
@Path("/driver")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DriverController {

    // DAO instance for handling driver database operations
    private DriverDAO dao = new DriverDAO();

    /**
     * Fetch all drivers
     * HTTP GET: /api/driver
     */
    @GET
    public List<Driver> getAll() { 
        return dao.getAllDrivers(); 
    }

    /**
     * Fetch driver by ID
     * HTTP GET: /api/driver/{id}
     */
    @GET
    @Path("/{id}")
    public Response getDriverById(@PathParam("id") int id) {

        Driver d = dao.getDriverById(id);

        // Return 404 if driver does not exist
        if (d == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Driver not found\"}")
                    .build();        
        } else {
            return Response.ok(d).build();
        }
    }

    /**
     * Add a new driver
     * HTTP POST: /api/driver
     */
    @POST
    public Response add(Driver d, @Context UriInfo ui) {

        if (dao.addDriver(d)) {
            UriBuilder b = ui.getAbsolutePathBuilder()
                             .path(Integer.toString(d.getDriverId()));

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\":\"Driver added successfully\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Driver Insert Failed\"}")
                    .build();   
        }
    }

    /**
     * Add multiple drivers at once
     * HTTP POST: /api/driver/multipledriver
     */
    @POST
    @Path("/multipledriver") 
    public Response add(List<Driver> drivers) {

        boolean result = dao.addMultipleDrivers(drivers);

        if (result) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\":\"Drivers Added Successfully\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Driver Insert Failed\"}")
                    .build();
        }
    }

    /**
     * Update driver details
     * HTTP PUT: /api/driver/{id}
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Driver d) {

        d.setDriverId(id);

        if (dao.updateDriver(d)){
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Driver updated successfully!\"}")
                    .build();       
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Driver update failed!\"}")
                    .build();          
        }
    }

    /**
     * Update driver status
     * HTTP PUT: /api/driver/updateStatus/{id}
     */
    @PUT
    @Path("/updateStatus/{id}")
    public Response updateStatus(@PathParam("id") int id, Driver d) {

        d.setDriverId(id);

        if (dao.updateDriverStatus(d)){
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Driver status updated successfully!\"}")
                    .build();        
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Driver status update failed!\"}")
                    .build();           
        }
    }

    /**
     * Bulk update drivers
     * HTTP PUT: /api/driver/bulk
     */
    @PUT
    @Path("/bulk")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bulkUpdateDrivers(List<Driver> drivers) {

        try {
            List<Driver> updatedDrivers = dao.bulkUpdateDrivers(drivers);

            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Bulk update successful.\"}")
                    .build();          
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Bulk update failed: " + e.getMessage() + "\"}")
                    .build();           
        }
    }

    /**
     * Delete a driver by ID
     * HTTP DELETE: /api/driver/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {

        if (dao.deleteDriver(id)) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Driver deleted successfully!\"}")
                    .build();
        } else {          
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Driver deletion failed!\"}")
                    .build();
        }
    }

    /**
     * Bulk delete drivers
     * HTTP DELETE: /api/driver/bulk
     */
    @DELETE
    @Path("/bulk")
    public Response bulkDeleteDrivers(List<Integer> driverIds) {

        try {
            for (Integer id : driverIds) {
                if (!dao.deleteDriver(id)) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Failed to delete driver with ID: " + id)
                            .build();         
                }
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"All drivers deleted successfully.\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Bulk deletion failed: " + e.getMessage() + "\"}")
                    .build();       
        }
    }
}
