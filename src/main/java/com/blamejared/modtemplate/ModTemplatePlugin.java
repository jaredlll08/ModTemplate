package com.blamejared.modtemplate;

import org.gradle.api.*;

import java.io.*;


public class ModTemplatePlugin implements Plugin<Project> {
    
    @Override
    public void apply(Project project) {
        
        project.task("genGitChangelog").doLast(task -> {
            File file = new File("test.md");
            System.out.println(file.getAbsolutePath());
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            File test = project.file("test");
            System.out.println(test.getAbsolutePath());
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                bufferedWriter.write("Test!");
            } catch(Exception e) {
                e.printStackTrace();
            }
            
        });
    }
    
}
