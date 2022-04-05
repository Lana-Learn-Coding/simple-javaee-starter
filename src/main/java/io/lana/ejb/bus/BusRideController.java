package io.lana.ejb.bus;

import io.lana.ejb.lib.servlet.AbstractJsonController;
import io.lana.ejb.lib.servlet.Viewable;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/bus")
public class BusRideController extends AbstractJsonController<BusRide, Integer> {
    @Inject
    protected BusRideController(BusRideRepo repo) {
        super(repo);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable view() {
        return new Viewable("/bus/index.jsp");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@QueryParam("search") String search) {
        return Response.ok(repo.list("it.name like ?1", "%" + search + "%")).build();
    }
}
