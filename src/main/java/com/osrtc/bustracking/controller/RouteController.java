package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.RouteDAO;
import com.osrtc.bustracking.model.Route;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * REST Controller for Route-related operations.
 * Manages bus routes including creation, updates, and deletions.
 *
 * Base URL: /api/route
 */
@Path("/route")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RouteController {

    // DAO instance responsible for route persistence
    private RouteDAO dao = new RouteDAO();

    /**
     * Retrieve all routes.
     * HTTP GET: /api/route
     */
    @GET
    public List<Route> getAll() { 
        return dao.getAllRoutes(); 
    }

    /**
     * Retrieve a route by ID.
     * HTTP GET: /api/route/{id}
     */
    @GET
    @Path("/{id}")
    public Response getRouteById(@PathParam("id") int id) {

        Route r = dao.getRouteById(id);

        // Return 404 if route does not exist
        if (r == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Route not found\"}")
                    .build();
        } else {
            return Response.ok(r).build();
        }
    }

    /**
     * Create a new route.
     * HTTP POST: /api/route
     */
    @POST
    public Response add(Route r, @Context UriInfo uriInfo) {

        if (dao.addRoute(r)) {

            // Build URI for the newly created route
            UriBuilder builder = uriInfo.getAbsolutePathBuilder()
                                        .path(Integer.toString(r.getRouteId()));

            return Response.created(builder.build())
                           .entity(r)
                           .build();
        } else {
            // Note: Message text preserved as-is per original code
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Route added successfully")
                           .build();
        } 
    }

    /**
     * Add multiple routes at once.
     * HTTP POST: /api/route/multipleroutes
     */
    @POST
    @Path("/multipleroutes")
    public Response add(List<Route> routes) {

        boolean result = dao.addMultipleRoutes(routes);

        if (result) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\":\"Routes Added Successfully\"}")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Route Insert Failed\"}")
                    .build();
        }
    }

    /**
     * Update a route by ID.
     * HTTP PUT: /api/route/{id}
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Route r) {

        r.setRouteId(id);

        if (dao.updateRoute(r)) {
            return Response.ok()
                    .entity("{\"message\":\"Route updated successfully\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Update Failed\"}")
                    .build();
        } 
    }

    /**
     * Bulk update routes.
     * HTTP PUT: /api/route/bulk
     */
    @PUT
    @Path("/bulk")
    public Response bulkUpdateRoutes(List<Route> routes) {

        List<Route> failedUpdates = dao.bulkUpdateRoutes(routes);

        if (failedUpdates.isEmpty()) {
            return Response.ok("{\"message\":\"All routes updated successfully\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Bulk update failed for some routes\"}")
                    .build();
        }
    }

    /**
     * Delete a route by ID.
     * HTTP DELETE: /api/route/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {

        if (dao.deleteRoute(id)){
            return Response.ok("{\"message\":\"Route deleted successfully\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Route not found\"}")
                    .build();
        }
    }

    /**
     * Bulk delete routes.
     * HTTP DELETE: /api/route/bulk
     */
    @DELETE
    @Path("/bulk")
    public Response bulkDeleteRoutes(List<Integer> routeIds) {

        try {
            for (Integer id : routeIds) {
                if (!dao.deleteRoute(id)) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Failed to delete route with ID: " + id)
                            .build();         
                }
            }
            return Response.ok("{\"message\":\"All routes deleted successfully\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();    
        }
    }
}
