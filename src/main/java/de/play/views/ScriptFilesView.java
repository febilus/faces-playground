package de.play.views;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.play.controller.ScriptFile;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ScriptFilesView {

    private final Set<ScriptFile> wantedFiles = new HashSet<>();

    public void addWanted(ScriptFile... scriptFiles) {
        if (scriptFiles != null) {
            this.wantedFiles.addAll(Arrays.asList(scriptFiles));
        }
    }

    public List<String> getResources() {
        return ScriptFile.getSortedResources(wantedFiles);
    }

}
