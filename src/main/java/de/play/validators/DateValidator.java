package de.play.validators;

import de.play.session.ActiveLocale;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@RequestScoped
@FacesValidator(value = "play.DateValidator", managed = true)
public class DateValidator implements Validator<LocalDate> {

    @Inject
    private ActiveLocale activeLocale;

    public void validate(FacesContext context, UIComponent component, LocalDate value) throws ValidatorException {
        if (value != null) {
            LocalDate minDate = getDateFromAttribute(component, "minDate");
            if (minDate != null && value.isBefore(minDate)) {
                String message = getFormattedMessage("de.play.faces.validator.DateValidator.INVALID_MIN_DATE", minDate);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
            LocalDate maxDate = getDateFromAttribute(component, "maxDate");
            if (maxDate != null && value.isAfter(maxDate)) {
                String message = getFormattedMessage("de.play.faces.validator.DateValidator.INVALID_MAX_DATE", maxDate);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        }
    }

    private LocalDate getDateFromAttribute(UIComponent component, String attributeName) {
        Object obj = (Object) component.getAttributes().get(attributeName);
        if (obj == null) {
            return null;
        }
        if (obj instanceof String str) {
            return LocalDate.from(DateTimeFormatter.ISO_DATE.parse(str));
        }
        if (obj instanceof LocalDate localDate) {
            return localDate;
        }
        return null;
    }

    private String getFormattedMessage(String i18nKey, LocalDate localDate) {
        String formattedDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).localizedBy(activeLocale.getCurrentLocale()).format(localDate);
        return String.format(activeLocale.resolve(i18nKey), formattedDate);
    }

}
