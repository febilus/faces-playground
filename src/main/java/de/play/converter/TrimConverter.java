package de.play.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 * Alle Parameter eines Requests werden beim Eingang getrimmt. Bindet sich an
 * String,java
 */
@FacesConverter(forClass = String.class)
public class TrimConverter implements Converter<String> {

    @Override
    public String getAsObject(FacesContext context, UIComponent component, String modelValue) {
        return modelValue == null ? "" : modelValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }
        String trimmed = submittedValue.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

}
