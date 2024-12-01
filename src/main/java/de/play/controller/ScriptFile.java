package de.play.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum ScriptFile {

    GLOBAL(2, "global.js"),
    GLOBAL_2(3, "global-2.js"),
    GLOBAL_3(4, "global-3.js");

    int position;
    String resource;

    ScriptFile(int position, String resource) {
        this.position = position;
        this.resource = resource;
    }

    public static List<String> getSortedResources(Set<ScriptFile> wantedFiles) {
        return Arrays.asList(values()).stream()
                .filter(v -> wantedFiles.contains(v))
                .sorted((o1, o2) -> Integer.compare(o1.position, o2.position))
                .map(v -> v.resource)
                .collect(Collectors.toList());
    }

}
