package io.lana.ejb.lib.conf;

import io.lana.ejb.App;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

/**
 * Config Jersey server feature
 */
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages(App.class.getPackage().getName());
        register(JspMvcFeature.class);
        register(LoggingFeature.class);
    }
}
