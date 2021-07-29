package com.blamejared.modtemplate;

import com.blamejared.modtemplate.extensions.ModTemplateExtension;
import groovy.json.JsonSlurper;
import org.gradle.api.Project;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

public class Utils {
    
    public static void injectSecrets(Project project) {
        
        String secret_file = System.getenv().getOrDefault(Constants.ENV_SECRET_FILE, Constants.PATH_SECRET_FILE);
        if(project.hasProperty(Constants.PROPERTY_SECRET_FILE)) {
            secret_file = (String) project.property(Constants.PROPERTY_SECRET_FILE);
        }
        File secretsFile = project.file(secret_file);
        project.getLogger().lifecycle("Injecting Secrets");
        if(secretsFile.exists()) {
            project.getLogger().lifecycle("Attempting to load secrets.");
            Object parsedSecrets = new JsonSlurper().parse(secretsFile);
            if(parsedSecrets instanceof Map) {
                @SuppressWarnings("unchecked") Map<String, Object> map = (Map<String, Object>) parsedSecrets;
                map.forEach((s, o) -> project.getExtensions().add(s, o));
                project.getLogger().lifecycle("Loaded " + map.size() + " secrets!");
            } else {
                project.getLogger()
                        .error("Unable to load Secrets as the JsonSlurper did not provide a Map, instead it provided: " + parsedSecrets
                                .getClass());
            }
        } else if(System.getenv().containsKey(Constants.ENV_SECRET_FILE)) {
            
            project.getLogger()
                    .warn("Unable to load " + Constants.ENV_SECRET_FILE + "as it does not exist! Tried: ");
            project.getLogger().warn(secret_file);
        } else {
            project.getLogger().lifecycle("Secrets file does not exist! Looked at: ");
            project.getLogger().lifecycle(secret_file);
            
        }
        project.getLogger().lifecycle("Done Injecting Secrets");
    }
    
    
    public static String getCIChangelog(Project project, ModTemplateExtension extension) {
        
        try {
            ByteArrayOutputStream stdout = new ByteArrayOutputStream();
            String gitHash = System.getenv("GIT_COMMIT");
            String gitPrevHash = System.getenv("GIT_PREVIOUS_COMMIT");
            String repo = extension.getChangelog()
                    .getRepo() + "/commit/";
            if(gitHash != null && gitPrevHash != null) {
                project.exec(execSpec -> execSpec.commandLine("git")
                        .args("log", "--pretty=tformat:- [%s](" + repo + "%H) - %aN ", gitPrevHash + "..." + gitHash)
                        .setStandardOutput(stdout));
                return stdout.toString().trim();
            } else if(gitHash != null) {
                project.exec(execSpec -> execSpec.commandLine("git")
                        .args("log", "--pretty=tformat:- [%s](" + repo + "%H) - %aN ", "-1", gitHash)
                        .setStandardOutput(stdout));
                return stdout.toString().trim();
            } else {
                return "Unavailable";
            }
        } catch(Exception ignored) {
            return "Unavailable";
        }
    }
    
}
