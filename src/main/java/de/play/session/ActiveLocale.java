package de.play.session;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class ActiveLocale implements Serializable {

    private Locale currentLocale;
    private List<Locale> availableLocales;

    @Inject
    FacesContext facesContext;

    @PostConstruct
    public void init() {
        Application app = facesContext.getApplication();
        currentLocale = app.getViewHandler().calculateLocale(facesContext);
        availableLocales = new ArrayList<>();
        availableLocales.add(app.getDefaultLocale());
        app.getSupportedLocales().forEachRemaining(availableLocales::add);
    }

    public void reload() {
        facesContext.getPartialViewContext().getEvalScripts().add("location.replace(location)");
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public String getLanguageTag() {
        return currentLocale.toLanguageTag();

    }

    public void setLanguageTag(String languageTag) {
        currentLocale = Locale.forLanguageTag(languageTag);
    }

    public List<Locale> getAvailableLocales() {
        return availableLocales;
    }

    public String resolve(String key) {
        ResourceBundle textBundle = ResourceBundle.getBundle("de.play.i18n.text", currentLocale);
        if (textBundle != null && textBundle.containsKey(key)) {
            return textBundle.getString(key);
        }
        ResourceBundle messageBundle = ResourceBundle.getBundle("de.play.i18n.messages", currentLocale);
        if (messageBundle != null && messageBundle.containsKey(key)) {
            return messageBundle.getString(key);
        }
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Missing Translation: '" + key + "'");
        return "???" + key + "???";
    }

}
