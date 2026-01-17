package de.play.views;

import de.play.jpa.entity.User;
import de.play.restServer.UsersClient;
import de.play.session.ActiveLocale;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Named
@RequestScoped
public class RestClientView {

    @Inject
    @RestClient
    UsersClient usersClient;

    @Inject
    FacesContext facesContext;
    @Inject
    ActiveLocale activeLocale;

    boolean showUsers;

    public List<User> getUsers() {
        return usersClient.getUsers();
    }

    public void doShowUsers() {
        showUsers = true;
    }

    public void createUser(AjaxBehaviorEvent event) {
        String id = event.getComponent().getClientId();

        User user = new User();

        user.setEmail(UUID.randomUUID() + "@atest.de");
        user.setPassword(String.valueOf(UUID.randomUUID()));
        user.setDateOfRegistration(LocalDateTime.now().minusYears(18));
        user.setBirthday(LocalDate.now().minusYears(23));
        Response response = usersClient.createUser(user);
        if (response.getStatus() == 200 && response.hasEntity()) {
            facesContext.addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, activeLocale.resolve("submit.created"), null));
        } else {
            facesContext.addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, activeLocale.resolve("technicalFailureTryAgainLater"), null));
        }
    }

    public boolean isShowUsers() {
        return showUsers;
    }

}
