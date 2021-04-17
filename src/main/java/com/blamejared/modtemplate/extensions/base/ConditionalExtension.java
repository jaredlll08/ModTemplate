package com.blamejared.modtemplate.extensions.base;

import org.gradle.api.Task;

public abstract class ConditionalExtension {
    private boolean enabled;
    
    public boolean isEnabled() {
        
        return enabled;
    }
    
    public void enabled(boolean enabled) {
        
        this.enabled = enabled;
    }
    
    public boolean onlyIf(Task task) {
        
        return enabled;
    }
    
}
