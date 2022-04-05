package io.lana.ejb.lib.servlet;

public class Viewable extends org.glassfish.jersey.server.mvc.Viewable {
    public Viewable(String templateName) throws IllegalArgumentException {
        super("/views" + templateName);
    }

    public Viewable(String templateName, Object model) throws IllegalArgumentException {
        super("/views" + templateName, model);
    }
}
