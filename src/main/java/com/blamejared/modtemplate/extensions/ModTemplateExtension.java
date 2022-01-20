package com.blamejared.modtemplate.extensions;

import org.gradle.api.Action;

public class ModTemplateExtension {
    
    private final ChangelogExtension changelog = new ChangelogExtension();
    private final VersionTrackerExtension versionTracker = new VersionTrackerExtension();
    private String mcVersion;
    private String curseHomepage;
    private String displayName;
    private String modLoader;
    
    public ChangelogExtension getChangelog() {
        
        return changelog;
    }
    
    public VersionTrackerExtension getVersionTracker() {
        
        return versionTracker;
    }
    
    public void versionTracker(Action<? super VersionTrackerExtension> versionTrackerAction) {
        
        versionTrackerAction.execute(versionTracker);
        if(versionTracker.getHomepage() == null || versionTracker.getHomepage().trim().isEmpty()) {
            versionTracker.homepage(getCurseHomepage());
        }
        if(versionTracker.getProjectName() == null || versionTracker.getProjectName().trim().isEmpty()) {
            versionTracker.projectName(getDisplayName());
        }
    }
    
    public void changelog(Action<? super ChangelogExtension> changelogAction) {
        
        changelogAction.execute(changelog);
    }
    
    public String getMcVersion() {
        
        return mcVersion;
    }
    
    public void mcVersion(String mcVersion) {
        
        this.mcVersion = mcVersion;
    }
    
    public String getCurseHomepage() {
        
        return curseHomepage;
    }
    
    public void curseHomepage(String curseHomepage) {
        
        this.curseHomepage = curseHomepage;
    }
    
    public String getDisplayName() {
        
        return displayName;
    }
    
    public void displayName(String displayName) {
        
        this.displayName = displayName;
    }
    
    public String getModLoader() {
        
        return modLoader;
    }
    
    public boolean hasModLoader() {
        
        return modLoader != null && !modLoader.trim().isEmpty();
    }
    
    public void modLoader(String modLoader) {
        
        this.modLoader = modLoader;
    }
    
}
