package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.LocationDAO;
import com.osrtc.bustracking.model.Location;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationController {
    private LocationDAO dao = new LocationDAO();

    @GET
    public List<Location> getAll() { return dao.getAllLocations(); }

    @GET
    @Path("/bus/{busId}")
    public List<Location> getByBus(@PathParam("busId") int busId) {
        return dao.getLocationsByBusId(busId);
    }

    @GET
    @Path("/bus/{busId}/latest")
    public Response getLatestByBus(@PathParam("busId") int busId) {
        Location l = dao.getLatestLocationByBusId(busId);
        if (l == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(l).build();
    }

    @POST
    public Response add(Location loc, @Context UriInfo ui) {
        if (dao.addLocation(loc)) {
            UriBuilder b = ui.getAbsolutePathBuilder().path(Integer.toString(loc.getLocationId()));
            return Response.created(b.build()).entity(loc).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
