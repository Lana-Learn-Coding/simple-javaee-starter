package io.lana.ejb.lib;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
