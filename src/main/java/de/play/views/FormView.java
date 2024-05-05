package de.play.views;

import de.play.controller.beans.AddressSuggestion;
import de.play.session.ActiveLocale;
import de.play.views.beans.FormBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Named
@RequestScoped
public class FormView {

    private static final Logger LOGGER = Logger.getLogger(FormView.class.getName());

    @Inject
    FacesContext facesContext;
    @Inject
    ActiveLocale activeLocale;

    @Inject
    FormBean bean;

    @PostConstruct
    public void init() {
    }

    public void submit(AjaxBehaviorEvent event) {
        LOGGER.info("submit!");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, activeLocale.resolve("thankYou"), null);
        facesContext.addMessage(null, message);
        bean.setSubmitted(true);
    }

    public void takeOverSuggestion(AddressSuggestion suggestion) {
        if (isSet(suggestion.getZip())) {
            bean.setZip(suggestion.getZip());
        }
        if (isSet(suggestion.getCity())) {
            bean.setCity(suggestion.getCity());
        }
        if (isSet(suggestion.getStreet())) {
            bean.setStreet(suggestion.getStreet());
        }
        bean.getSuggestions().clear();
    }

    public Map<String, String> getCountries() {
        Map<String, String> countries = new LinkedHashMap<>();
        countries.put("de", activeLocale.resolve("country.germany"));
        countries.put("fr", activeLocale.resolve("country.france"));
        return countries;
    }

    public String getMinBirthday() {
        return LocalDate.now().minusYears(100).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getMaxBirthday() {
        return LocalDate.now().minusYears(10).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public List<AddressSuggestion> getAddressSuggestions() {
        return bean.getSuggestions();
    }

    public FormBean getBean() {
        return bean;
    }

    private boolean isSet(String value) {
        return value != null && !value.isEmpty();
    }

}
