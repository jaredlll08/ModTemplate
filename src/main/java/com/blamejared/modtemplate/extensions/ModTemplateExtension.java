package com.blamejared.modtemplate.extensions;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

public class ModTemplateExtension {
    
    private ChangelogExtension changelog = new ChangelogExtension();
    private VersionTrackerExtension versionTracker = new VersionTrackerExtension();
    private WebhookExtension webhook = new WebhookExtension();
    private String mcVersion;
    private String curseHomepage;
    private String displayName;
    
    public ChangelogExtension getChangelog() {
        
        return changelog;
    }
    
    public VersionTrackerExtension getVersionTracker() {
        
        return versionTracker;
    }
    
    public WebhookExtension getWebhook() {
        
        return webhook;
    }
    
    public void versionTracker(@DelegatesTo(VersionTrackerExtension.class) Closure<VersionTrackerExtension> versionTrackerClosure) {
        
        versionTrackerClosure.setDelegate(versionTracker);
        versionTrackerClosure.call();
        if(versionTracker.getHomepage() == null || versionTracker.getHomepage().trim().isEmpty()) {
            versionTracker.homepage(getCurseHomepage());
        }
        if(versionTracker.getProjectName() == null || versionTracker.getProjectName().trim().isEmpty()) {
            versionTracker.projectName(getDisplayName());
        }
    }
    
    public void changelog(@DelegatesTo(ChangelogExtension.class) Closure<ChangelogExtension> changelogClosure) {
        
        changelogClosure.setDelegate(changelog);
        changelogClosure.call();
    }
    
    public void webhook(@DelegatesTo(WebhookExtension.class) Closure<WebhookExtension> webhookClosure) {
        
        webhookClosure.setDelegate(webhook);
        webhookClosure.call();
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
    
}
