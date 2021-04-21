package com.blamejared.modtemplate.tasks;

import com.blamejared.modtemplate.extensions.ModTemplateExtension;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenGitChangelog implements Action<Task> {
    
    @Override
    public void execute(Task task) {
        
        Project project = task.getProject();
        ModTemplateExtension extension = project.getExtensions().getByType(ModTemplateExtension.class);
        
        File file = project.file("changelog.md");
        String[] firstCommit = new String[] {extension.getChangelog().getFirstCommit()};
        
        try(ByteArrayOutputStream firstCommitOS = new ByteArrayOutputStream();
            ByteArrayOutputStream changesOS = new ByteArrayOutputStream();
            BufferedWriter changelogWriter = new BufferedWriter(new FileWriter(file))) {
            project.exec(execSpec -> execSpec.commandLine("git")
                    .args("log", "-i", "--grep=" + extension.getChangelog()
                            .getIncrementRegex(), "--grep=initial\\scommit", "--pretty=tformat:%H", "--date=local", extension
                            .getChangelog()
                            .getFirstCommit() + "..@{0}")
                    .setStandardOutput(firstCommitOS));
            String foundCommit = firstCommitOS.toString();
            if(foundCommit.trim().contains("\n")) {
                firstCommit[0] = foundCommit.split("\n")[0].trim();
            }
            
            project.exec(execSpec -> execSpec.commandLine("git")
                    .args("log", "--pretty=tformat:- [%s](" + extension.getChangelog()
                            .getRepo() + "/commit/%H) - %aN - %cd", "--max-parents=1", "--date=local", firstCommit[0] + "..@")
                    .setStandardOutput(changesOS));
            
            changelogWriter.write("### Current version: " + project.getVersion() + "\n" + changesOS);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
