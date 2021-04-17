package com.blamejared.modtemplate.extensions;

import com.blamejared.modtemplate.extensions.base.ConditionalExtension;
import org.gradle.api.Project;

public class VersionTrackerExtension extends ConditionalExtension {
    
    private String endpoint;
    private String author;
    private String homepage;
    private String uid;
    
    public void supplement(Project project) {
        
        this.endpoint = (String) project.getProperties().get("versionTrackerAPI");
        this.author = (String) project.getProperties().get("versionTrackerAuthor");
        this.homepage = (String) project.getProperties().get("versionTrackerHomepage");
        this.uid = (String) project.getProperties().get("versionTrackerKey");
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
    
}
