package de.play.faces;

import jakarta.faces.application.Resource;
import jakarta.faces.application.ResourceHandler;
import jakarta.faces.application.ResourceHandlerWrapper;
import jakarta.faces.application.ResourceWrapper;
import java.io.IOException;

public class VersionResourceHandler extends ResourceHandlerWrapper {

    public VersionResourceHandler(ResourceHandler wrapped) {
        super(wrapped);
    }

    @Override
    public Resource createResource(String name, String library) {
        Resource resource = super.createResource(name, library);

        if (resource == null) {
            return resource;
        }

        return new ResourceWrapper(resource) {
            @Override
            public String getRequestPath() {
                String url = super.getRequestPath();
                return url
                        + (url.contains("?") ? "&" : "?")
                        + "v="
                        + getLastModified();
            }

            private long getLastModified() {
                try {
                    return getWrapped().getURL().openConnection().getLastModified();
                } catch (IOException ignore) {
                    return 0;
                }
            }
        };

    }
}
