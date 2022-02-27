package io.lana.ejb.lib.servlet;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;

@RequestScoped
public class ServletContext {
    @Context
    protected ServletContext servlet;

    @Context
    protected HttpServletRequest req;

    @Context
    protected HttpServletResponse res;

    public ServletContext servlet() {
        return servlet;
    }

    public HttpServletResponse res() {
        return res;
    }

    public HttpServletRequest req() {
        return req;
    }

    public Viewable view(String templateName) {
        return new Viewable("/views" + templateName);
    }

    public Viewable view(String templateName, Object model) {
        return new Viewable("/views" + templateName, model);
    }

    public void forward(String templateName) {
        try {
            req.getRequestDispatcher("/WEB-INF/views" + templateName).forward(req, res);
        } catch (Exception e) {
            throw new ServerErrorException(500, e);
        }
    }

    public void redirect(String path) {
        try {
            res.sendRedirect(path);
        } catch (Exception e) {
            throw new ServerErrorException(500, e);
        }
    }
}
