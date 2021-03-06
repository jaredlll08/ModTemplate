package com.blamejared.modtemplate.extensions;

import com.blamejared.modtemplate.extensions.base.ConditionalExtension;

public class ChangelogExtension extends ConditionalExtension {
    
    private String repo;
    private String incrementRegex = "(?<!\\\\)version\\s+push";
    private String firstCommit;
    private String changelogFile = "changelog.md";
    
    public String getRepo() {
        
        return repo;
    }
    
    public void repo(String repo) {
        
        this.repo = repo;
    }
    
    public String getIncrementRegex() {
        
        return incrementRegex;
    }
    
    public void incrementRegex(String incrementRegex) {
        
        this.incrementRegex = incrementRegex;
    }
    
    public String getFirstCommit() {
        
        return firstCommit;
    }
    
    public void firstCommit(String firstCommit) {
        
        this.firstCommit = firstCommit;
    }
    
    public String getChangelogFile() {
        
        return changelogFile;
    }
    
    public void changelogFile(String changelogFile) {
        
        this.changelogFile = changelogFile;
    }
    
}
