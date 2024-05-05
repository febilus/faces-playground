package de.play.views.beans;

import de.play.controller.beans.AddressSuggestion;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
public class FormBean implements Serializable {

    private String firstname;
    private String email;
    private String termsAccepted;
    private String country;
    private String serialNumber;
    private LocalDate birthday;
    private String zip;
    private String city;
    private String street;

    private boolean submitted;
    private List<AddressSuggestion> suggestions = new ArrayList<>();

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(String termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<AddressSuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<AddressSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

}
