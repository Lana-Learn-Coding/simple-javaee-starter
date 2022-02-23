package io.lana.ejb;

import io.lana.ejb.lib.ViewableResource;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

@Path("/hello-servlet")
public class HelloResource extends ViewableResource {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable hello(@QueryParam("name") @DefaultValue("World") final String name) {
        return new Viewable("/hello.jsp", Collections.singletonMap("name", name));
    }
}
