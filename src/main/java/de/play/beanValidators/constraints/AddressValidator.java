package de.play.beanValidators.constraints;

import de.play.controller.AddressService;
import de.play.controller.beans.AddressValidationSummary;
import de.play.session.ActiveLocale;
import de.play.views.beans.Address;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Set;

public class AddressValidator implements ConstraintValidator<ValidAddress, Address> {

    @Inject
    AddressService addressService;
    @Inject
    ActiveLocale activeLocale;

    @Override
    public void initialize(ValidAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(Address address, ConstraintValidatorContext context) {

        AddressValidationSummary validationSummary = addressService.validate(address.getZip(), address.getCity(), address.getStreet());

        if (validationSummary.isValid()) {
            return true;
        } else {
            context.disableDefaultConstraintViolation(); // default message des Contraints nich benutzen

            for (Map.Entry<String, Set<String>> entry : validationSummary.getMessages().entrySet()) {

                for (String value : entry.getValue()) {
                    String message = activeLocale.resolve(value);

                    context.buildConstraintViolationWithTemplate(message)
                            .addPropertyNode(entry.getKey())
                            .addConstraintViolation();

                }

            }

            // TODO wie die Suggestions aus der validationSummary übergeben?
            // jakarta.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL, in web.xml führt dazu, dass die Eingabefelder geleert werden.
            return false;
        }
    }
}
