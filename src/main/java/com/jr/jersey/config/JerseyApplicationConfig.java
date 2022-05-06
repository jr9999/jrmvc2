package com.jr.jersey.config;

import com.jr.jersey.resources.HelloWorldJerseyResource;
//import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyApplicationConfig extends ResourceConfig {
    public JerseyApplicationConfig() {
        register(HelloWorldJerseyResource.class);
    }
}
