package com.blamejared.modtemplate.tasks;

import com.blamejared.modtemplate.Utils;
import com.blamejared.modtemplate.extensions.ModTemplateExtension;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenGitChangelog implements Action<Task> {
    
    @Override
    public void execute(Task task) {
        
        Project project = task.getProject();
        ModTemplateExtension extension = project.getExtensions().getByType(ModTemplateExtension.class);
        
        File file = project.file(extension.getChangelog().getChangelogFile());
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Utils.getFullChangelog(project));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
