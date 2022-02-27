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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractCrudController<T extends Audited<ID>, ID> {
    protected final String path;
    protected final Class<T> clazz;
    protected final CrudRepository<T> repo;

    @Inject
    protected Validator validator;

    @Inject
    protected ServletContext context;

    protected AbstractCrudController(CrudRepository<T> repo) {
        this.repo = repo;
        Class<?> originClazz = ModelUtils.getOriginalClass(this);
        this.clazz = ModelUtils.getGenericType(originClazz);
        this.path = originClazz.getAnnotation(Path.class).value();
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
    public Viewable index(@BeanParam Pageable pageable) {
        Page<T> page = repo.page(pageable);
        return context.view(path + "/index.jsp", page);
    }

    @GET
    @Path("create")
    @Produces(MediaType.TEXT_HTML)
    public Viewable create() {
        context.req().setAttribute("errors", Collections.emptyMap());
        return context.view(path + "/create.jsp", ModelUtils.construct(clazz));
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_HTML)
    public void store(@BeanParam final T entity) {
        if (entity.getId() != null && repo.exist("it.id = ?1", entity.getId())) {
            context.req().setAttribute("errors", Collections.singletonMap("id", "id was already taken"));
            context.req().setAttribute("it", entity);
            context.forward(path + "/create.jsp");
        }

        Map<String, String> errors = validate(entity);
        if (errors.isEmpty()) {
            repo.save(entity);
            context.redirect(path);
        }

        context.req().setAttribute("errors", errors);
        context.req().setAttribute("it", entity);
        context.forward(path + "/create.jsp");
    }

    @GET
    @Path("delete")
    @Produces(MediaType.TEXT_HTML)
    public void destroy(@QueryParam("id") final ID id) {
        repo.delete("it.id = ?1", id);
        context.redirect(path);
    }

    @GET
    @Path("update")
    @Produces(MediaType.TEXT_HTML)
    public Viewable edit(@QueryParam("id") final ID id) {
        context.req().setAttribute("errors", Collections.emptyMap());
        T item = repo.first("it.id = ?1", id).orElseThrow(NotFoundException::new);
        return context.view(path + "/edit.jsp", item);
    }


    @POST
    @Path("update")
    public void edit(@QueryParam("id") final ID id, @BeanParam final T entity) {
        if (id == null) throw new NotFoundException("ID not found (empty)");
        if (!repo.exist("it.id = ?1", id)) throw new NotFoundException();

        Map<String, String> errors = validate(entity);
        if (errors.isEmpty()) {
            entity.setId(id);
            repo.save(entity);
            context.redirect(path + "/detail?id=" + id);
        }

        context.req().setAttribute("errors", errors);
        context.req().setAttribute("it", entity);
        context.forward(path + "/edit.jsp");
    }

    @GET
    @Path("detail")
    @Produces(MediaType.TEXT_HTML)
    public Viewable show(@QueryParam("id") final ID id) {
        T item = repo.first("it.id = ?1", id).orElseThrow(NotFoundException::new);
        return context.view(path + "/detail", item);
    }
}
