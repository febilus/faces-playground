package de.play.views.beans;

import de.play.beanValidators.constraints.ValidAddress;
import de.play.beanValidators.groups.AddressGroup;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@ValidAddress(groups = AddressGroup.class)
public class Address implements Serializable, Cloneable {

    @NotBlank(groups = AddressGroup.class)
    private String zip;
    @NotBlank(groups = AddressGroup.class)
    private String city;
    @NotBlank(groups = AddressGroup.class)
    private String street;
    private String country;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Address other = (Address) super.clone();
        other.setZip(this.getZip());
        other.setCity(this.getCity());
        other.setStreet(this.getStreet());
        other.setCountry(this.getCountry());
        return other;
    }

}
