package com.osrtc.bustracking.controller;

// Jackson imports (currently unused, may be for future JSON processing)
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// DAO and Model imports
import com.osrtc.bustracking.dao.BusDAO;
import com.osrtc.bustracking.model.Bus;
import com.osrtc.bustracking.model.FullBusData;

// Service layer imports
import com.osrtc.bustracking.service.AdminService;
import com.osrtc.bustracking.service.BusService;
import com.osrtc.bustracking.service.DriverService;
import com.osrtc.bustracking.service.LocationService;
import com.osrtc.bustracking.service.RouteService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * REST Controller for Bus-related operations
 * Base URL: /api/bus
 */
@Path("/bus")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BusController {

    // Direct DAO usage for database operations
    BusDAO dao = new BusDAO();

    // Service layer for complex business logic
    private BusService busService = new BusService();

    /**
     * Fetch all buses
     * HTTP GET: /api/bus
     */
    @GET
    public List<Bus> getAll() {
        return dao.getAllBuses();
    }

    /**
     * Fetch a bus by ID
     * HTTP GET: /api/bus/{id}
     */
    @GET
    @Path("/{id}")
    public Response getBusById(@PathParam("id") int id) {
        Bus b = dao.getBusById(id);

        // If bus not found, return 404
        if (b == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Bus not found\"}")
                    .build();
        } else {
            return Response.ok(b).build();
        }
    }

    /**
     * Add a new bus
     * HTTP POST: /api/bus
     */
    @POST
    public Response add(Bus b) {
        if (dao.addBus(b)){
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\":\"Bus added successfully\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Bus addition failed\"}")
                    .build();
        }
    }

    /**
     * Add multiple buses in one request
     * HTTP POST: /api/bus/multiplebus
     */
    @POST
    @Path("/multiplebus")
    public Response addMultipleBuses(List<Bus> buses) {
        boolean isAdded = dao.multipleBusInsert(buses);

        if (isAdded){
            return Response.ok("{\"message\":\" Buses added successfully\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Failed to add multiple buses\"}")
                    .build();
        }
    }

    /**
     * Update a bus by ID
     * HTTP PUT: /api/bus/{id}
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Bus b) {
        b.setBusId(id);

        if (dao.updateBus(b)){
            return Response.ok(b)
                    .entity("{\"message\":\"Bus updated successfully\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Bus not found\"}")
                    .build();
        }
    }

    /**
     * Bulk update buses
     * HTTP PUT: /api/bus/bulk
     */
    @PUT
    @Path("/bulk")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bulkUpdateBuses(List<Bus> buses) {
        try {
            return Response.ok(busService.bulkUpdateBuses(buses))
                    .entity("\"message\":\"Selected Bus updated successful\"")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Delete a bus by ID
     * HTTP DELETE: /api/bus/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (dao.deleteBus(id)){
            return Response.ok()
                    .entity("{\"message\":\"Bus deleted successfully\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Bus not found\"}")
                    .build();
        }
    }

    /**
     * Bulk delete buses
     * HTTP DELETE: /api/bus/bulk
     */
    @DELETE
    @Path("/bulk")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bulkDeleteBuses(List<Integer> busIds) {
        try {
            for (Integer id : busIds) {
                if (!dao.deleteBus(id)) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Failed to delete bus with ID: " + id)
                            .build();
                }
            }
            return Response.ok("{\"message\":\"All buses deleted successfully\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Assign a driver to a bus
     * HTTP PUT: /api/bus/assignDriver/{busId}/{driverId}
     */
    @PUT
    @Path("/assignDriver/{busId}/{driverId}")
    public Response assignDriverToBus(@PathParam("busId") int busId,
                                      @PathParam("driverId") int driverId) {

        boolean isAssigned = dao.assignDriverToBus(busId, driverId);

        if (isAssigned) {
            return Response.ok("{\"message\":\"Driver assigned to bus successfully\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Failed to assign driver to bus\"}")
                    .build();
        }
    }

    /**
     * Update bus operational status
     * HTTP PUT: /api/bus/updateStatus/{busId}/{status}
     */
    @PUT
    @Path("/updateStatus/{busId}/{status}")
    public Response updateBusStatus(@PathParam("busId") int busId,
                                    @PathParam("status") String status) {

        boolean isUpdated = dao.updateBusStatus(busId, status);

        if (isUpdated) {
            return Response.ok("{\"message\":\"Bus status updated successfully\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Failed to update bus status\"}")
                    .build();
        }
    }

    /**
     * Add full bus-related data in one request
     * Includes Route, Driver, Bus, Location, and Admin
     * HTTP POST: /api/bus/addfulldata
     */
    @POST
    @Path("/addfulldata")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFullBusData(FullBusData data) {
        try {
            RouteService routeService = new RouteService();
            DriverService driverService = new DriverService();
            BusService busService = new BusService();
            LocationService locationService = new LocationService();
            AdminService adminService = new AdminService();

            // Step 1: Add route
            routeService.addRoute(data.getRoute());

            // Step 2: Add driver
            driverService.addDriver(data.getDriver());

            // Step 3: Add bus with route & driver references
            data.getBus().setRouteId(data.getRoute().getRouteId());
            data.getBus().setDriverId(data.getDriver().getDriverId());
            busService.addBus(data.getBus());

            // Step 4: Add location for the bus
            data.getLocation().setBusId(data.getBus().getBusId());
            locationService.addLocation(data.getLocation());

            // Step 5: Add admin details (if required)
            adminService.addAdmin(data.getAdmin());

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"All entities added successfully!\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
