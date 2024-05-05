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
@Table(name = "zip_city")
public class ZipCity implements Serializable {

    private static final long serialVesionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "zip", nullable = false, length = 255)
    private String zip;

    @NotNull
    @Column(name = "city", nullable = false, length = 255)
    private String city;

    @NotNull
    @Column(name = "city_normalized", nullable = false, length = 255)
    private String cityNormalized;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityNormalized() {
        return cityNormalized;
    }

    public void setCityNormalized(String cityNormalized) {
        this.cityNormalized = cityNormalized;
    }

}
