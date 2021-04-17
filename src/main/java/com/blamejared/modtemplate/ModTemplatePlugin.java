package com.blamejared.modtemplate;

import com.blamejared.modtemplate.actions.DiscordWebhook;
import com.blamejared.modtemplate.extensions.ModTemplateExtension;
import com.blamejared.modtemplate.tasks.GenGitChangelog;
import com.blamejared.modtemplate.tasks.UpdateVersionTracker;
import org.gradle.api.Plugin;
import org.gradle.api.Project;


public class ModTemplatePlugin implements Plugin<Project> {
    
    private Project project;
    private ModTemplateExtension extension;
    
    @Override
    public void apply(Project project) {
        
        this.project = project;
        this.extension = project.getExtensions().create("modTemplate", ModTemplateExtension.class);
        Utils.updateVersion(project);
        Utils.injectSecrets(project);
        this.extension.getVersionTracker().supplement(project);
        this.extension.getWebhook().supplement(project);
        
        project.task("genGitChangelog").doLast(new GenGitChangelog()).onlyIf(this.extension.getChangelog()::onlyIf);
        project.task("updateVersionTracker")
                .doLast(new UpdateVersionTracker())
                .onlyIf(this.extension.getVersionTracker()::onlyIf);
        
        
        project.getTasks()
                .getByName("curseforge")
                .doLast(new DiscordWebhook())
                .onlyIf(this.extension.getWebhook()::onlyIf);
    }
    
}
