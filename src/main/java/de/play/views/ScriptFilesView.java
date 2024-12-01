package de.play.views;

import de.play.controller.ScriptFile;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
@RequestScoped
public class ScriptFilesView {

    private Set<ScriptFile> wantedFiles = new HashSet<>();

    public void addWanted(ScriptFile... scriptFiles) {
        if (scriptFiles != null) {
            for (ScriptFile file : scriptFiles) {
                this.wantedFiles.add(file);
            }
        }
    }

    public List<String> getResources() {
        return ScriptFile.getSortedResources(wantedFiles);
    }

}
