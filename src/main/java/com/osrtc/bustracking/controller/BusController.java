package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.BusDAO;
import com.osrtc.bustracking.model.Bus;
// import com.osrtc.bustracking.service.BusService;
import com.osrtc.bustracking.model.FullBusData;
import com.osrtc.bustracking.service.AdminService;
import com.osrtc.bustracking.service.BusService;
import com.osrtc.bustracking.service.DriverService;
import com.osrtc.bustracking.service.LocationService;
import com.osrtc.bustracking.service.RouteService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/bus")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BusController {
    BusDAO dao = new BusDAO();
    // private BusDAO service = new BusDAO();

    @GET
    public List<Bus> getAll() {
        return dao.getAllBuses();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Bus b = dao.getBusById(id);
        if (b == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(b).build();
    }

    @POST
    public Response add(Bus b) {
        if (dao.addBus(b))
            return Response.status(Response.Status.CREATED).entity(b).build();
        return Response.status(Response.Status.BAD_REQUEST).entity("buse added successfully").build();
    }
    
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Bus b) {
        b.setBusId(id);
        if (dao.updateBus(b))
            return Response.ok(b).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (dao.deleteBus(id))
            return Response.ok().build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    
    // ✅ New bulk insert endpoint
    @POST
    @Path("/multiplebus")
    public Response addMultipleBuses(List<Bus> buses) {
        boolean isAdded = dao.addMultipleBuses(buses);
        if (isAdded)
            return Response.ok("{\"message\":\"Multiple buses added successfully\"}").build();
        else
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Failed to add multiple buses\"}").build();
    }

    @POST
    @Path("/addFullData")
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

            // Step 3: Add bus with linked route & driver
            data.getBus().setRouteId(data.getRoute().getRouteId());
            data.getBus().setDriverId(data.getDriver().getDriverId());
            busService.addBus(data.getBus());

            // Step 4: Add location for this bus
            data.getLocation().setBusId(data.getBus().getBusId());
            locationService.addLocation(data.getLocation());

            // Step 5: Add admin (if needed)
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
