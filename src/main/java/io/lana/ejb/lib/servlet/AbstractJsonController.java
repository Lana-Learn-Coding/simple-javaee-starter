package io.lana.ejb.lib.servlet;

import io.lana.ejb.lib.pageable.Page;
import io.lana.ejb.lib.pageable.Pageable;
import io.lana.ejb.lib.repo.Audited;
import io.lana.ejb.lib.repo.CrudRepository;
import io.lana.ejb.lib.utils.ModelUtils;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractJsonController<T extends Audited<ID>, ID> {
    protected final Class<T> clazz;
    protected final CrudRepository<T> repo;

    @Inject
    protected Validator validator;

    protected AbstractJsonController(CrudRepository<T> repo) {
        this.repo = repo;
        Class<?> originClazz = ModelUtils.getOriginalClass(this);
        this.clazz = ModelUtils.getGenericType(originClazz);
    }

    protected Map<String, String> validate(Object model) {
        Set<ConstraintViolation<Object>> violations = validator.validate(model);
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<Object> violation : violations) {
            String field = "";
            for (Node node : violation.getPropertyPath()) {
                field = node.getName();
            }

            errors.put(field, violation.getMessage());
        }
        return errors;
    }

    // No @Produces allow subclass to overwrite for specific type
    @GET
    public Response index(@BeanParam final Pageable pageable) {
        Page<T> page = repo.page(pageable);
        return Response.ok(page).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response store(final T entity) {
        if (entity.getId() != null && repo.exist("it.id = ?1", entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Collections.singletonMap("id", "id was already taken")).build();
        }

        Map<String, String> errors = validate(entity);
        if (errors.isEmpty()) {
            return Response.ok(repo.save(entity)).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("id") final ID id) {
        repo.delete("it.id = ?1", id);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") final ID id, final T entity) {
        if (id == null) throw new NotFoundException("ID not found (empty)");
        if (!repo.exist("it.id = ?1", id)) throw new NotFoundException();

        Map<String, String> errors = validate(entity);
        if (errors.isEmpty()) {
            entity.setId(id);
            return Response.ok(repo.save(entity)).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id") final ID id) {
        T item = repo.first("it.id = ?1", id).orElseThrow(NotFoundException::new);
        return Response.ok(item).build();
    }
}
