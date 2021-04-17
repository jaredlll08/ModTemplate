package com.blamejared.modtemplate.tasks;

import com.blamejared.modtemplate.extensions.ModTemplateExtension;
import com.blamejared.modtemplate.extensions.VersionTrackerExtension;
import groovy.json.JsonOutput;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateVersionTracker implements Action<Task> {
    
    
    @Override
    public void execute(Task task) {
        
        try {
            Project project = task.getProject();
            ModTemplateExtension extension = project.getExtensions().getByType(ModTemplateExtension.class);
            VersionTrackerExtension versionTracker = extension.getVersionTracker();
            String endpoint = versionTracker.getEndpoint();
            
            Map<String, String> body = new HashMap<>();
            body.put("author", versionTracker.getAuthor());
            body.put("projectName", extension.getProjectName());
            body.put("gameVersion", extension.getMcVersion());
            body.put("projectVersion", (String) project.getVersion());
            body.put("homepage", versionTracker.getHomepage());
            body.put("uid", versionTracker.getUid());
            
            HttpsURLConnection req = (HttpsURLConnection) new URL(endpoint).openConnection();
            req.setRequestMethod("POST");
            req.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            req.setRequestProperty("User-Agent", extension.getProjectName() + " Tracker Gradle");
            req.setDoOutput(true);
            req.getOutputStream().write(JsonOutput.toJson(body).getBytes(StandardCharsets.UTF_8));
            
            project.getLogger().lifecycle("VersionTracker Status Code: " + req.getResponseCode());
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
                project.getLogger()
                        .lifecycle("VersionTracker Response: " + reader.lines().collect(Collectors.joining("\n")));
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
