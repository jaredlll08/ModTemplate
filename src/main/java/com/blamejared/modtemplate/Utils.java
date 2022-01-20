package com.blamejared.modtemplate;

import com.blamejared.modtemplate.extensions.ModTemplateExtension;
import groovy.json.JsonSlurper;
import org.gradle.api.Project;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Utils {
    
    public static String updatingVersion(String version) {
        
        return updatingVersion(version, ".");
    }
    
    public static String updatingSemVersion(String version) {
        
        return updatingVersion(version, "+");
    }
    
    public static String updatingVersion(String version, String separator) {
        
        if(System.getenv().containsKey(Constants.ENV_BUILD_NUMBER)) {
            version += separator + System.getenv(Constants.ENV_BUILD_NUMBER);
        }
        return version;
    }
    
    public static String locateProperty(Project project, String name) {
        
        if(project.hasProperty(name)) {
            return (String) project.property(name);
        }
        return System.getenv(name);
    }
    
    public static void injectSecrets(Project project) {
        
        String secretFile = System.getenv().getOrDefault(Constants.ENV_SECRET_FILE, Constants.PATH_SECRET_FILE);
        if(project.hasProperty(Constants.PROPERTY_SECRET_FILE)) {
            secretFile = (String) project.property(Constants.PROPERTY_SECRET_FILE);
        }
        if(secretFile == null) {
            project.getLogger().lifecycle("Provided secret file location was null!");
            return;
        }
        File secretsFile = project.file(secretFile);
        project.getLogger().lifecycle("Injecting Secrets from secrets file");
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
            project.getLogger().warn(secretFile);
        } else {
            project.getLogger().lifecycle("Secrets file does not exist! Looked at: ");
            project.getLogger().lifecycle(secretFile);
            
        }
        project.getLogger().lifecycle("Done Injecting Secrets");
    }
    
    
    public static String getCIChangelog(Project project) {
        
        try {
            ModTemplateExtension extension = project.getExtensions().getByType(ModTemplateExtension.class);
            
            ByteArrayOutputStream stdout = new ByteArrayOutputStream();
            String gitHash = System.getenv("GIT_COMMIT");
            String gitPrevHash = System.getenv("GIT_PREVIOUS_COMMIT");
            String repo = extension.getChangelog().getRepo() + "/commit/";
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
    
    public static String getFullChangelog(Project project) {
        
        ModTemplateExtension extension = project.getExtensions().findByType(ModTemplateExtension.class);
        if(extension == null) {
            return "Cannot generate a changelog for projects with the modtemplate extension!";
        }
        
        String[] firstCommit = new String[] {extension.getChangelog().getFirstCommit()};
        
        StringBuilder builder = new StringBuilder("### Current version: ").append(project.getVersion());
        try(ByteArrayOutputStream firstCommitOS = new ByteArrayOutputStream();
            ByteArrayOutputStream changesOS = new ByteArrayOutputStream()) {
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
            
            builder.append("\n").append(changesOS);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
    
}
