package de.play.restServer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import java.io.IOException;
import java.util.logging.Logger;

@ApplicationScoped
public class RestClientLoggingFilter implements ClientRequestFilter, ClientResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(RestClientLoggingFilter.class.getName());

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        LOGGER.info(new StringBuilder()
                .append("REQUEST ")
                .append(requestContext.getMethod())
                .append(" ")
                .append(requestContext.getUri().toString())
                .toString());
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        LOGGER.info(new StringBuilder()
                .append("RESPONSE Status ")
                .append(responseContext.getStatus())
                .append(" ")
                .append(responseContext.getStatusInfo())
                .toString());
    }

}
