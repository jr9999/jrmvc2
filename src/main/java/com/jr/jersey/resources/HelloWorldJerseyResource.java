package com.jr.jersey.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldJerseyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldJerseyResource.class);

    public HelloWorldJerseyResource() {
        LOGGER.info("initialize HelloWorldJerseyResource");
    }

    @POST
    public void receiveHello() {
        LOGGER.info("hello");
    }
}
