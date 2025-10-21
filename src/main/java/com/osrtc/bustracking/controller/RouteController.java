package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.RouteDAO;
import com.osrtc.bustracking.model.Route;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/route")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RouteController {
    private RouteDAO dao = new RouteDAO();

    @GET
    public List<Route> getAll() { return dao.getAllRoutes(); }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Route r = dao.getRouteById(id);
        if (r == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(r).build();
    }

    @POST
    public Response add(Route r, @Context UriInfo uriInfo) {
        if (dao.addRoute(r)) {
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Integer.toString(r.getRouteId()));
            return Response.created(builder.build()).entity(r).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Route r) {
        r.setRouteId(id);
        if (dao.updateRoute(r)) return Response.ok(r).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (dao.deleteRoute(id)) return Response.ok().build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
