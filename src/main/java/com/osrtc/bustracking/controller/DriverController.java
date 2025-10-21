package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.DriverDAO;
import com.osrtc.bustracking.model.Driver;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/driver")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DriverController {
    private DriverDAO dao = new DriverDAO();

    @GET
    public List<Driver> getAll() { return dao.getAllDrivers(); }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Driver d = dao.getDriverById(id);
        if (d == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(d).build();
    }

    @POST
    public Response add(Driver d, @Context UriInfo ui) {
        if (dao.addDriver(d)) {
            UriBuilder b = ui.getAbsolutePathBuilder().path(Integer.toString(d.getDriverId()));
            return Response.created(b.build()).entity(d).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Driver d) {
        d.setDriverId(id);
        if (dao.updateDriver(d)) return Response.ok(d).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (dao.deleteDriver(id)) return Response.ok().build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
