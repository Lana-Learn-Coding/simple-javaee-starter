package io.lana.ejb;

import io.lana.ejb.lib.ServletContext;
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
    private ServletContext context;

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
        HelloEntity entity = new HelloEntity();
        context.em().persist(entity);
        context.em().flush();
        return Response.ok(entity).build();
    }
}
