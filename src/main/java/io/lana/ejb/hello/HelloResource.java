package io.lana.ejb.hello;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/hello-servlet")
public class HelloResource {
    @Inject
    private HelloRepo repo;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable hello(@QueryParam("name") @DefaultValue("World") final String name) {
        return new Viewable("/views/hello.jsp", Collections.singletonMap("name", name));
    }

    @GET
    @Path("/entity")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response hello() {
        HelloEntity entity = repo.save(new HelloEntity());
        return Response.ok(entity).build();
    }
}
