package de.play.views.beans;

import jakarta.faces.view.ViewScoped;
import java.io.Serializable;

@ViewScoped
public class UserManagementBean implements Serializable {

    private boolean showRegisterForm;

    public boolean isShowRegisterForm() {
        return showRegisterForm;
    }

    public void setShowRegisterForm(boolean showRegisterForm) {
        this.showRegisterForm = showRegisterForm;
    }

}
