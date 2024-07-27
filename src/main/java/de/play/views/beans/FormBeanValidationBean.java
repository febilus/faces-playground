package de.play.views.beans;

import de.play.controller.beans.AddressSuggestion;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
public class FormBeanValidationBean implements Serializable {

    @NotBlank
    private String firstname;
    private String email;
    private String termsAccepted;
    private String country;
    private String serialNumber;
    private LocalDate birthday;
    private Address address;
    private boolean submitted;
    private List<AddressSuggestion> suggestions;

    @PostConstruct
    public void init() {
        this.address = new Address();
        this.suggestions = new ArrayList<>();
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<AddressSuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<AddressSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

}
