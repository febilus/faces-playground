package de.play.restServer;

import java.io.Serializable;

public record Product(
        String id,
        String name,
        String manufacturer
        ) implements Serializable {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

}
