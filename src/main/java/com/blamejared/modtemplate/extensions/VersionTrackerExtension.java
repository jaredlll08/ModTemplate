package com.blamejared.modtemplate.extensions;

import com.blamejared.modtemplate.Utils;
import com.blamejared.modtemplate.extensions.base.ConditionalExtension;
import org.gradle.api.Project;

public class VersionTrackerExtension extends ConditionalExtension {
    
    private String endpoint;
    private String author;
    private String homepage;
    private String uid;
    private String projectName;
    
    public void supplement(Project project) {
        
        this.endpoint = Utils.locateProperty(project, "versionTrackerAPI");
        this.author = Utils.locateProperty(project, "versionTrackerAuthor");
        this.homepage = Utils.locateProperty(project, "versionTrackerHomepage");
        this.uid = Utils.locateProperty(project, "versionTrackerKey");
        this.projectName = Utils.locateProperty(project, "versionTrackerProjectName");
    }
    
    public void endpoint(String endpoint) {
        
        this.endpoint = endpoint;
    }
    
    public void author(String author) {
        
        this.author = author;
    }
    
    public void homepage(String homepage) {
        
        this.homepage = homepage;
    }
    
    public void key(String key) {
        
        this.uid = key;
    }
    
    public String getEndpoint() {
        
        return endpoint;
    }
    
    public String getAuthor() {
        
        return author;
    }
    
    public String getHomepage() {
        
        return homepage;
    }
    
    public String getUid() {
        
        return uid;
    }
    
    public void uid(String uid) {
        
        this.uid = uid;
    }
    
    public String getProjectName() {
        
        return projectName;
    }
    
    public void projectName(String projectName) {
        
        this.projectName = projectName;
    }
    
}
