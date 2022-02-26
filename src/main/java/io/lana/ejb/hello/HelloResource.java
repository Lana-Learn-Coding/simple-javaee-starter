package io.lana.ejb.hello;


import io.lana.ejb.lib.servlet.ServletContext;
import io.lana.ejb.lib.servlet.Viewable;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/hello-servlet")
public class HelloResource {
    @Inject
    private ServletContext context;

    @Inject
    private HelloRepo repo;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable hello(@QueryParam("name") @DefaultValue("World") final String name) {
        return context.view("/hello.jsp", Collections.singletonMap("name", name));
    }

    @GET
    @Path("/entity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        HelloEntity entity = repo.save(new HelloEntity());
        return Response.ok(entity).build();
    }
}
