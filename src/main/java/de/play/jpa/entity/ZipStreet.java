package de.play.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "zip_street")
public class ZipStreet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "zip", nullable = false, length = 255)
    private String zip;

    @NotNull
    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @NotNull
    @Column(name = "street_normalized", nullable = false, length = 255)
    private String streetNormalized;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNormalized() {
        return streetNormalized;
    }

    public void setStreetNormalized(String streetNormalized) {
        this.streetNormalized = streetNormalized;
    }

}
