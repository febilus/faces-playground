package de.play.validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@FacesValidator(value = "play.EmailValidator", managed = false)
public class EmailValidator implements Validator<String> {

    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Override
    public void validate(FacesContext context, UIComponent component, String email) throws ValidatorException {
        if (email == null || email.trim().isEmpty()) {
            return;
        }

        if (!PATTERN_EMAIL.matcher(email).matches()) {
            String message = getMessage(context, "de.play.faces.validator.EmailValidator.MESSAGE");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    private String getMessage(FacesContext context, String key) {
        return ResourceBundle.getBundle(context.getApplication().getMessageBundle(), context.getViewRoot().getLocale()).getString(key);
    }

}
