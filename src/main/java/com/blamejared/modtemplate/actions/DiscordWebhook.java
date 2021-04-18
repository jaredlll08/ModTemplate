package com.blamejared.modtemplate.actions;

import com.blamejared.modtemplate.Utils;
import com.blamejared.modtemplate.extensions.ModTemplateExtension;
import com.diluv.schoomp.Webhook;
import com.diluv.schoomp.message.Message;
import com.diluv.schoomp.message.embed.Embed;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.IOException;
import java.lang.reflect.Field;

public class DiscordWebhook implements Action<Task> {
    
    @Override
    public void execute(Task task) {
        
        Project project = task.getProject();
        ModTemplateExtension extension = project.getExtensions().getByType(ModTemplateExtension.class);
        
        Object mainArtifact = project.getTasks()
                .getByName("curseforge" + extension.getWebhook().getCurseId())
                .property("mainArtifact");
        
        String newFileId = getFileId(mainArtifact);
        if(newFileId.isEmpty()) {
            project.getLogger().error("Unable to get fileId from artifact!");
            return;
        }
        
        Webhook webhook = new Webhook(extension.getWebhook().getUrl(), extension.getDisplayName());
        
        Message message = new Message();
        message.setUsername(extension.getDisplayName());
        message.setContent(String.format("%s %s for Minecraft %s has been released! The download will be available soon.", extension
                .getDisplayName(), project.getVersion(), extension.getMcVersion()));
        
        Embed embed = new Embed();
        embed.addField("Download", extension.getCurseHomepage() + "/files/" + newFileId, false);
        embed.addField("Change Log", Utils.getCIChangelog(project, extension), false);
        embed.setColor(0xF16436);
        
        message.addEmbed(embed);
        message.setAvatarUrl(extension.getWebhook().getAvatarUrl());
        try {
            webhook.sendMessage(message);
        } catch(IOException e) {
            project.getLogger().error("Error while sending Discord Webhook!");
            e.printStackTrace();
        }
        
    }
    
    private String getFileId(Object obj) {
        
        try {
            Field foundField = obj.getClass().getDeclaredField("fileID");
            foundField.setAccessible(true);
            return foundField.get(obj) + "";
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
}
