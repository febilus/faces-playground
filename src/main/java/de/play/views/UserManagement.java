package de.play.views;

import de.play.jpa.dao.UserDAO;
import de.play.jpa.entity.User;
import de.play.session.ActiveLocale;
import de.play.views.beans.UserManagementBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionListener;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class UserManagement {

    private static final Logger LOGGER = Logger.getLogger(UserManagement.class.getName());

    @Inject
    FacesContext facesContext;
    @Inject
    ActiveLocale activeLocale;

    @Inject
    UserDAO userDao;
    @Inject
    UserManagementBean userManagementBean;

    public List<User> listUsers() {
        try {
            return userDao.list();
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
    }

    public UserManagementBean getBean() {
        return userManagementBean;
    }

    public ActionListener getRegisterActionListener() {
        LOGGER.info("UserManagement getRegisterActionListener");
        userManagementBean.setShowRegisterForm(false);
        ActionListener listener = actionEvent -> {
            LOGGER.info("UserManagement getRegisterActionListener 2");
        };
        return listener;
    }

}
