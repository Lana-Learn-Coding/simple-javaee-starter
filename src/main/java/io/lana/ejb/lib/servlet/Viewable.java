package io.lana.ejb.lib.servlet;

public class Viewable extends org.glassfish.jersey.server.mvc.Viewable {
    Viewable(String templateName) throws IllegalArgumentException {
        super(templateName);
    }

    Viewable(String templateName, Object model) throws IllegalArgumentException {
        super(templateName, model);
    }
}
