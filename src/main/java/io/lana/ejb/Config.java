package io.lana.ejb;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class Config extends ResourceConfig {
    public Config() {
        packages(getClass().getPackage().getName());
        register(JspMvcFeature.class);
        register(LoggingFeature.class);
    }
}
