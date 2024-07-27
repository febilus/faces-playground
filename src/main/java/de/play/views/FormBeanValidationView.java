package de.play.views;

import de.play.controller.beans.AddressSuggestion;
import de.play.session.ActiveLocale;
import de.play.views.beans.FormBeanValidationBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Named
@RequestScoped
public class FormBeanValidationView {

    private static final Logger LOGGER = Logger.getLogger(FormBeanValidationView.class.getName());

    @Inject
    FacesContext facesContext;
    @Inject
    ActiveLocale activeLocale;

    @Inject
    FormBeanValidationBean bean;

    @PostConstruct
    public void init() {
    }

    @Inject
    Validator validator;

    public void submit(AjaxBehaviorEvent event) {
        // pragmatische Lösung über Aufruf Validierung bei Submit
        // Nachteil: hier kommt man nur rein, wenn die Phase 3 (Validierun) erfolgreich war
//        Set<ConstraintViolation<Address>> violations = validator.validate(bean.getAddress());
//        if (!violations.isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(violations.iterator().next().getMessage()));
//        }
//
        LOGGER.info("submit!");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, activeLocale.resolve("submit.thankYou"), null);
        facesContext.addMessage(null, message);
        bean.setSubmitted(true);
    }

    public void takeOverSuggestion(AddressSuggestion suggestion) {
        if (isSet(suggestion.getZip())) {
            bean.getAddress().setZip(suggestion.getZip());
        }
        if (isSet(suggestion.getCity())) {
            bean.getAddress().setCity(suggestion.getCity());
        }
        if (isSet(suggestion.getStreet())) {
            bean.getAddress().setStreet(suggestion.getStreet());
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

    public FormBeanValidationBean getBean() {
        return bean;
    }

    private boolean isSet(String value) {
        return value != null && !value.isEmpty();
    }

}
