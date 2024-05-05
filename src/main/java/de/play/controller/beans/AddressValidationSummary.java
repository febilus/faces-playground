package de.play.controller.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddressValidationSummary {

    private boolean valid;
    private Map<String, Set<String>> messages = new HashMap<>();
    private List<AddressSuggestion> suggestions = new ArrayList<>();

    public Map<String, Set<String>> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Set<String>> messages) {
        this.messages = messages;
    }

    public List<AddressSuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<AddressSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
