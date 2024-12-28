package de.play.views;

import de.play.jpa.dao.UserDAO;
import de.play.jpa.entity.User;
import de.play.session.ActiveLocale;
import de.play.views.beans.RegisterBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class RegisterFormView {

    private static final Logger LOGGER = Logger.getLogger(RegisterFormView.class.getName());

    @Inject
    FacesContext facesContext;
    @Inject
    ActiveLocale activeLocale;

    @Inject
    RegisterBean bean;

    @Inject
    UserDAO userDAO;

    @PostConstruct
    public void init() {
    }

    public boolean showForm(boolean showSubmittedForm) {
        if (showSubmittedForm) {
            return true;
        }
        return !bean.isSubmitted();
    }

    public void submit(AjaxBehaviorEvent event) {
        try {
            LOGGER.info("RegisterFormView submit!");
            User user = new User();
            user.setEmail(bean.getEmail());
            user.setBirthday(bean.getBirthday());
            user.setPassword(bean.getPassword());
            userDAO.create(user);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, activeLocale.resolve("submit.thankYou"), null);
            facesContext.addMessage(null, message);
            bean.setSubmitted(true);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failure in register submit", e);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, activeLocale.resolve("technicalFailureTryAgainLater"), null);
            facesContext.addMessage(null, message);
            bean.setSubmitted(false);
        }
    }

    public String getMinBirthday() {
        return LocalDate.now().minusYears(100).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getMaxBirthday() {
        return LocalDate.now().minusYears(14).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public RegisterBean getBean() {
        return bean;
    }

}
