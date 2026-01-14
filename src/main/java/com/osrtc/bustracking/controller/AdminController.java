package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.AdminDAO;
import com.osrtc.bustracking.model.Admin;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * REST controller for Admin-related operations.
 * Base URL: /api/admin
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {

    // Data Access Object for Admin operations
    private final AdminDAO dao = new AdminDAO();

    /**
     * Retrieve all admin records.
     * HTTP GET: /api/admin
     */
    @GET
    public List<Admin> getAll() {
        return dao.getAllAdmins();
    }

    /**
     * Retrieve a specific admin by ID.
     * HTTP GET: /api/admin/{id}
     */
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {

        Admin admin = dao.getAdminById(id);

        // Return 404 if admin does not exist
        if (admin == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // NOTE: Password should not be returned in real applications
        return Response.ok(admin).build();
    }

    /**
     * Create a new admin.
     * HTTP POST: /api/admin
     */
    @POST
    public Response add(Admin admin, @Context UriInfo uriInfo) {

        boolean created = dao.addAdmin(admin);

        if (created) {
            // Build URI for the newly created resource
            UriBuilder builder = uriInfo.getAbsolutePathBuilder()
                                        .path(String.valueOf(admin.getAdminId()));

            return Response.created(builder.build())
                           .entity(admin)
                           .build();
        }

        // Invalid input or failure
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Update an existing admin.
     * HTTP PUT: /api/admin/{id}
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Admin admin) {

        admin.setAdminId(id);

        if (dao.updateAdmin(admin)) {
            return Response.ok(admin).build();
        }

        // Admin not found
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Delete an admin by ID.
     * HTTP DELETE: /api/admin/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {

        if (dao.deleteAdmin(id)) {
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Admin login endpoint.
     * HTTP POST: /api/admin/login
     *
     * WARNING:
     * This implementation is NOT secure for production.
     */
    @POST
    @Path("/login")
    public Response login(Credentials credentials) {

        Admin admin = dao.getAdminByUsername(credentials.username);

        if (admin != null && admin.getPassword().equals(credentials.password)) {
            return Response.ok(admin).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Simple credentials DTO for login requests.
     * Should be replaced by a proper DTO in real applications.
     */
    public static class Credentials {
        public String username;
        public String password;
    }
}
