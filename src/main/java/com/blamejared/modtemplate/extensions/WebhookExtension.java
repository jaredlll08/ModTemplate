package com.blamejared.modtemplate.extensions;

import com.blamejared.modtemplate.extensions.base.ConditionalExtension;
import org.gradle.api.Project;

public class WebhookExtension extends ConditionalExtension {
    
    private String curseId;
    private String url;
    private String avatarUrl;
    
    public void supplement(Project project) {
        
        this.url = (String) project.getProperties().get("discordCFWebhook");
    }
    
    public String getCurseId() {
        
        return curseId;
    }
    
    public void curseId(String curseId) {
        
        this.curseId = curseId;
    }
    
    public String getUrl() {
        
        return url;
    }
    
    public void url(String url) {
        
        this.url = url;
    }
    
    public String getAvatarUrl() {
        
        return avatarUrl;
    }
    
    public void avatarUrl(String avatarUrl) {
        
        this.avatarUrl = avatarUrl;
    }
    
}
