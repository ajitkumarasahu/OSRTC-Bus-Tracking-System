package com.osrtc.bustracking.controller;

import com.osrtc.bustracking.dao.AdminDAO;
import com.osrtc.bustracking.model.Admin;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {
    private AdminDAO dao = new AdminDAO();

    @GET
    public List<Admin> getAll() { return dao.getAllAdmins(); }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Admin a = dao.getAdminById(id);
        if (a == null) return Response.status(Response.Status.NOT_FOUND).build();
        // For security, you might normally not return the password. This sample returns it raw.
        return Response.ok(a).build();
    }

    @POST
    public Response add(Admin a, @Context UriInfo ui) {
        if (dao.addAdmin(a)) {
            UriBuilder b = ui.getAbsolutePathBuilder().path(Integer.toString(a.getAdminId()));
            return Response.created(b.build()).entity(a).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Admin a) {
        a.setAdminId(id);
        if (dao.updateAdmin(a)) return Response.ok(a).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (dao.deleteAdmin(id)) return Response.ok().build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // Simple login endpoint (NOT secure for production; use hashed passwords and tokens instead)
    @POST
    @Path("/login")
    public Response login(Credentials creds) {
        Admin a = dao.getAdminByUsername(creds.username);
        if (a != null && a.getPassword().equals(creds.password)) {
            return Response.ok(a).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    // small nested static POJO for login in this controller
    public static class Credentials {
        public String username;
        public String password;
    }
}
