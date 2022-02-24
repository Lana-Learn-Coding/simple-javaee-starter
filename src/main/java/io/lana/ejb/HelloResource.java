package io.lana.ejb;

import io.lana.ejb.lib.ViewableResource;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/hello-servlet")
public class HelloResource extends ViewableResource {
    @PersistenceContext
    private EntityManager em;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable hello(@QueryParam("name") @DefaultValue("World") final String name) {
        return new Viewable("/hello.jsp", Collections.singletonMap("name", name));
    }

    @GET
    @Path("/entity")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response hello() {
        HelloEntity entity = new HelloEntity();
        em.persist(entity);
        em.flush();
        return Response.ok(entity).build();
    }
}
