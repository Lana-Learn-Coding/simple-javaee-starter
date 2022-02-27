package io.lana.ejb.customer;

import io.lana.ejb.lib.pageable.Page;
import io.lana.ejb.lib.pageable.Pageable;
import io.lana.ejb.lib.servlet.AbstractCrudController;
import io.lana.ejb.lib.servlet.ServletContext;
import io.lana.ejb.lib.servlet.Viewable;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerController extends AbstractCrudController<Customer, Integer> {
    @Inject
    private ServletContext context;

    @Inject
    protected CustomerController(CustomerRepo repo) {
        super(repo);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable index(@QueryParam("search") @DefaultValue("") String search, @BeanParam Pageable pageable) {
        Page<Customer> result = repo.page(pageable, "it.name like ?1 or it.email like ?1", "%" + search + "%");
        return context.view("/customers/index.jsp", result);
    }
}
