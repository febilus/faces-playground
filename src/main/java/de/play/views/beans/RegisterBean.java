package de.play.views.beans;

import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;

@ViewScoped
public class RegisterBean implements Serializable {

    private String email;
    private String name;
    private String password;
    private LocalDate birthday;
    private boolean submitted;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

}
