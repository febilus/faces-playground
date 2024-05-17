package de.play.validators;

import de.play.controller.AddressService;
import de.play.controller.beans.AddressSuggestion;
import de.play.controller.beans.AddressValidationSummary;
import de.play.session.ActiveLocale;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Wenn Managed, sind injects möglich
 */
@RequestScoped
@FacesValidator(value = "play.AddressValidator", managed = true)
public class AddressValidator implements Validator<String> {

    @Inject
    private AddressService addressService;
    @Inject
    private ActiveLocale activeLocale;

    private boolean validatedInCurrentRequest;

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        if (validatedInCurrentRequest) {
            return;
        }
        if (isSkipValidator(context)) {
            validatedInCurrentRequest = true;
            return;
        }

        String componentIds = (String) component.getAttributes().get("componentIds");
        String[] splittetIds = componentIds.split(",");

        HtmlInputText zipComponent = findInputComponent(component, splittetIds[0]);
        HtmlInputText cityComponent = findInputComponent(component, splittetIds[1]);
        HtmlInputText streetComponent = findInputComponent(component, splittetIds[2]);
        List<AddressSuggestion> suggestionsContainer = (List<AddressSuggestion>) component.getAttributes().get("suggestionContainer");

        String zip = getCurrentValue(zipComponent);
        String city = getCurrentValue(cityComponent);
        String street = getCurrentValue(streetComponent);

        AddressValidationSummary validationSummary = addressService.validate(zip, city, street);
        validatedInCurrentRequest = true;
        if (validationSummary.isValid()) {
            suggestionsContainer.clear();
        } else {

            if (!validationSummary.getSuggestions().isEmpty()) {
                suggestionsContainer.clear();
                suggestionsContainer.addAll(validationSummary.getSuggestions());
            }

            Map<String, List<FacesMessage>> messages = mapToFacesMessages(validationSummary);

            addFacesMessagesToInputs(messages, context, zipComponent, cityComponent, streetComponent);

            validatedInCurrentRequest = true;
            if (!messages.isEmpty()) {
                String message = activeLocale.resolve("ADDRESS_INVALID");
//            List<FacesMessage> facesMessags = messages.values().stream()
//                    .flatMap(entry -> entry.stream())
//                    .collect(Collectors.toList());
//            throw new ValidatorException(facesMessags);
                throw new ValidatorException(new FacesMessage());
            }

        }
    }

    private HtmlInputText findInputComponent(UIComponent component, String id) {
        if (component.getId().equals(id) && component instanceof HtmlInputText) {
            return (HtmlInputText) component;
        }
        Object obj = component.findComponent(id);
        if (obj instanceof HtmlInputText) {
            return (HtmlInputText) component.findComponent(id);
        }
        return null;
    }

    private String getCurrentValue(HtmlInputText inputText) {
        if (inputText.getSubmittedValue() != null) {
            return (String) inputText.getSubmittedValue();
        }
        return (String) inputText.getValue();
    }

    private AddressValidationSummary validateAddress(HtmlInputText zipComponent, HtmlInputText cityComponent, HtmlInputText streetComponent) {
        String zip = zipComponent.getSubmittedValue() != null ? (String) zipComponent.getSubmittedValue() : (String) zipComponent.getValue();
        String city = cityComponent.getSubmittedValue() != null ? (String) cityComponent.getSubmittedValue() : (String) cityComponent.getValue();
        String street = streetComponent.getSubmittedValue() != null ? (String) streetComponent.getSubmittedValue() : (String) streetComponent.getValue();
        return addressService.validate(zip, city, street);
    }

    private Map<String, List<FacesMessage>> mapToFacesMessages(AddressValidationSummary summary) {
        Map<String, List<FacesMessage>> messages = new HashMap<>();
        for (Entry<String, Set<String>> entry : summary.getMessages().entrySet()) {
            for (String value : entry.getValue()) {
                String message = activeLocale.resolve(value);
                messages.putIfAbsent(entry.getKey(), new ArrayList<>());
                messages.get(entry.getKey()).add(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        }
        return messages;
    }

    private void addFacesMessagesToInputs(Map<String, List<FacesMessage>> messages, FacesContext context, HtmlInputText zipComponent, HtmlInputText cityComponent, HtmlInputText streetComponent) {
        for (Entry<String, List<FacesMessage>> entry : messages.entrySet()) {
            for (FacesMessage message : entry.getValue()) {
                switch (entry.getKey()) {
                    case "zip" ->
                        context.addMessage(zipComponent.getClientId(), message);
                    case "city" ->
                        context.addMessage(cityComponent.getClientId(), message);
                    case "street" ->
                        context.addMessage(streetComponent.getClientId(), message);
                }
            }
        }
    }

    private boolean isSkipValidator(FacesContext context) {
        String source = context.getExternalContext().getRequestParameterMap().get("skipAddressValidator");
        return "true".equals(source);
    }

}
