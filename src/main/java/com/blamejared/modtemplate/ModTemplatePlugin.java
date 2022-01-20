package com.blamejared.modtemplate;

import com.blamejared.modtemplate.extensions.ModTemplateExtension;
import com.blamejared.modtemplate.tasks.GenGitChangelog;
import com.blamejared.modtemplate.tasks.UpdateVersionTracker;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ModTemplatePlugin implements Plugin<Project> {
    
    private ModTemplateExtension extension;
    
    @Override
    public void apply(Project project) {
        
        this.extension = project.getExtensions().create("modTemplate", ModTemplateExtension.class);
        Utils.injectSecrets(project);
        this.extension.getVersionTracker().supplement(project);
        
        project.task("genGitChangelog").doLast(new GenGitChangelog()).onlyIf(this.extension.getChangelog()::onlyIf);
        project.task("updateVersionTracker")
                .doLast(new UpdateVersionTracker())
                .onlyIf(this.extension.getVersionTracker()::onlyIf);
        
    }
    
}
