package io.lana.ejb;

import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;

import java.io.File;

public class App {
    public static void main(String[] args) {
        try {
            // Config port
            GlassFishProperties glassfishProperties = new GlassFishProperties();
            glassfishProperties.setPort("http-listener", 8080);
            glassfishProperties.setPort("https-listener", 8181);

            GlassFish glassfish = GlassFishRuntime.bootstrap().newGlassFish(glassfishProperties);
            glassfish.start();

            // Config files
            Deployer deployer = glassfish.getDeployer();
            ScatteredArchive archive = new ScatteredArchive(
                App.class.getSimpleName(),
                ScatteredArchive.Type.WAR,
                new File("src/main/webapp")
            );
            archive.addClassPath(new File("target", "classes"));
            archive.addMetadata(new File("src/main/webapp/WEB-INF", "web.xml"));
            deployer.deploy(archive.toURI(), "--contextroot=/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
