package de.play.controller;

import de.play.controller.beans.AddressSuggestion;
import de.play.controller.beans.AddressValidationSummary;
import de.play.jpa.dao.ZipCityDAO;
import de.play.jpa.dao.ZipStreetDAO;
import de.play.jpa.entity.ZipCity;
import de.play.jpa.entity.ZipStreet;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

@ApplicationScoped
public class AddressService {

    private final Pattern STREET_NUMBER_PATTERN;

    @Inject
    private ZipCityDAO zipCityDAO;
    @Inject
    private ZipStreetDAO zipStreetDAO;

    private final JaroWinklerSimilarity jaroWinklerSimilarity;
    private final JaccardSimilarity jaccardSimilarity;

    public AddressService() {
        this.STREET_NUMBER_PATTERN = Pattern.compile("([0-9]+.*)");
        jaroWinklerSimilarity = new JaroWinklerSimilarity();
        jaccardSimilarity = new JaccardSimilarity();
    }

    public AddressValidationSummary validate(String zip, String city, String street) {
        String cityNormalized = normalize(city);
        String streetNormalized = normalize(street);
        String streetNumber = findStreetNumber(street);

        List<ZipCity> zipCities = findMatchingZipOrCity(zip, cityNormalized);
        List<ZipStreet> zipStreets;

        if (zipCities.size() == 1) {
            zipStreets = findMatchingZipOrStreet(zipCities.get(0).getZip(), streetNormalized);
        } else {
            zipStreets = findMatchingZipOrStreet(zip, street);
        }

        Map<String, Set<String>> messages = new HashMap<>();

        if (isSet(zip) && zipCities.isEmpty()) {
            messages.putIfAbsent("zip", new HashSet<>());
            messages.get("zip").add("ZIP_INVALID");
        }

        if (isSet(city) && zipCities.isEmpty()) {
            messages.putIfAbsent("city", new HashSet<>());
            messages.get("city").add("CITY_INVALID");
        }
        if (isSet(city) && zipCities.size() >= 1 && zipCities.stream().noneMatch(z -> z.getCityNormalized().equals(cityNormalized))) {
            messages.putIfAbsent("city", new HashSet<>());
            messages.get("city").add("CITY_INVALID");
        }

        if (isSet(street) && zipStreets.isEmpty()) {
            messages.putIfAbsent("street", new HashSet<>());
            messages.get("street").add("STREET_INVALID");
        }
        if (isSet(street) && zipStreets.size() >= 1 && zipStreets.stream().noneMatch(z -> z.getStreetNormalized().equals(streetNormalized))) {
            messages.putIfAbsent("street", new HashSet<>());
            messages.get("street").add("STREET_INVALID");
        }
        if (isSet(street) && !isSet(streetNumber)) {
            messages.putIfAbsent("street", new HashSet<>());
            messages.get("street").add("STREET_INVALID");
        }

        AddressValidationSummary summary = new AddressValidationSummary();
        summary.setValid(messages.isEmpty());
        summary.setMessages(messages);
        if (!messages.isEmpty()) {
            summary.setSuggestions(mapToSuggestions(zipCities, zipStreets, streetNumber));
        }

        return summary;
    }

    private List<ZipCity> findMatchingZipOrCity(String zip, String cityNormalized) {
        List<ZipCity> possibleMatching = new ArrayList<>();
        if (isSet(zip) || isSet(cityNormalized)) {
            List<ZipCity> zipCitys = zipCityDAO.findByZipOrCity(zip, cityNormalized);

            for (ZipCity zipCity : zipCitys) {
                if (zipCity.getZip().equals(zip)) {
                    possibleMatching.add(zipCity);
                    return possibleMatching;
                }
            }
            for (ZipCity zipCity : zipCitys) {
                if (zipCity.getCityNormalized().equals(cityNormalized)) {
                    possibleMatching.add(zipCity);
                    return possibleMatching;
                }
            }
            for (ZipCity zipCity : zipCitys) {
                if (isSimilar(zipCity.getCityNormalized(), cityNormalized)) {
                    possibleMatching.add(zipCity);
                }
            }
        }
        return possibleMatching;
    }

    private List<ZipStreet> findMatchingZipOrStreet(String zip, String streetNormalized) {
        List<ZipStreet> possibleMatching = new ArrayList<>();
        if (isSet(zip) || isSet(streetNormalized)) {
            List<ZipStreet> zipStreets = zipStreetDAO.findByZipOrStreet(zip, streetNormalized);

            for (ZipStreet zipStreet : zipStreets) {
                if (zipStreet.getZip().equals(zip)) {
                    if (isSet(streetNormalized)) {
                        if (zipStreet.getStreetNormalized().equals(streetNormalized)) {
                            possibleMatching.clear();
                            possibleMatching.add(zipStreet);
                            return possibleMatching;
                        }
                        if (isSimilar(zipStreet.getStreetNormalized(), streetNormalized)) {
                            possibleMatching.add(zipStreet);
                        }
                    } else {
                        possibleMatching.add(zipStreet);
                    }
                }
            }
        }
        return possibleMatching;
    }

    private List<AddressSuggestion> mapToSuggestions(List<ZipCity> zipCities, List<ZipStreet> zipStreets, String streetNumber) {
        List<AddressSuggestion> list = new ArrayList<>();
        boolean hasStreetInCity = false;
        for (ZipCity zipCity : zipCities) {
            for (ZipStreet zipStreet : zipStreets) {
                if (zipCity.getZip().equals(zipStreet.getZip())) {
                    AddressSuggestion suggestion = new AddressSuggestion();
                    suggestion.setZip(zipCity.getZip());
                    suggestion.setCity(zipCity.getCity());
                    if (isSet(streetNumber)) {
                        suggestion.setStreet(zipStreet.getStreet() + " " + streetNumber);
                    } else {
                        suggestion.setStreet(zipStreet.getStreet());
                    }
                    list.add(suggestion);
                    hasStreetInCity = true;
                }
            }
            if (!hasStreetInCity) {
                AddressSuggestion suggestion = new AddressSuggestion();
                suggestion.setZip(zipCity.getZip());
                suggestion.setCity(zipCity.getCity());
                list.add(suggestion);
            }
        }
        return list;
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replaceFirst("(.+)[0-9].*", "$1")
                .replaceAll(" ", "")
                .replaceAll("ß", "ss")
                .replaceAll("-", "")
                .replaceAll("Str.", "Strasse")
                .replaceAll("-", "")
                .replaceAll("\\.", "")
                .replaceAll("[0-9]", "")
                .replaceAll("ä", "ae")
                .replaceAll("ö", "oe")
                .replaceAll("ü", "ue")
                .trim()
                .toLowerCase();
    }

    private boolean isSimilar(String value1, String value2) {
        double jaro = jaroWinklerSimilarity.apply(value1, value2);
        boolean simimilar = jaro >= 0.86;
        if (simimilar) {
            return true;
        }
        Double jaccard = jaccardSimilarity.apply(value1, value2);
        if (jaccard != null && jaccard > 0.4) {
            return true;
        }
        return false;
    }

    private boolean isSet(String value) {
        return value != null && !value.isEmpty();
    }

    private String findStreetNumber(String street) {
        Matcher matcher = STREET_NUMBER_PATTERN.matcher(street);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

}
