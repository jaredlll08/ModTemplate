# ModTemplate

# Using

Add the BlameJared maven to your `buildscript`.

```groovy
buildscript {
    repositories {
        maven { url = 'https://maven.blamejared.com' }
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath group: 'com.blamejared', name: 'ModTemplate', version: '1.+', changing: true
    }
}
```

Apply the plugin.

```groovy
apply plugin: 'com.blamejared.modtemplate'
```

# Options

```groovy
modTemplate {
    // Minecraft Version of this project.
    mcVersion "1.16.5"
    // Link to the CurseForge project, used to generate file links and for the update tracker.
    curseHomepage "https://www.curseforge.com/minecraft/mc-mods/slimyboyos"
    // Project name, used in the Discord webhook and the version tracker.
    projectName "SlimyBoyos"

    // Changelog generation
    changelog {
        // Enable changelog.md generation.
        enabled true
        // First commit of this branch / when the changelog should start generating from.
        firstCommit "02b4d3b3df95b146faf41ee962ff57a2c2a8a432"
        // Repository link for markdown links to link to exact commit.
        repo "https://github.com/CraftTweaker/CraftTweaker"
        // Set by default, so you don't need this line. Used to determine when to start a new changelog (Won't have any commit before commits with this message).
        incrementRegex "(?<!\\\\)version\\s+push"
    }
    // Forge Version Tracker.
    // Note: Currently uses a proprietary closed source system.
    // All of these values can be provided by the secrets file.
    versionTracker {
        // Enable version tracking.
        enabled true
        // Endpoint to post the new version to.
        // Secret value: "`versionTrackerAPI`".
        // NOTE: This is printed in the log if a `MalformedURLException` is thrown!
        endpoint "https://updates.example.com"
        // Project Homepage To be displayed in the output json.
        // Secret value: "`versionTrackerAuthor`".
        // Optional, will be replaced by `curseHomepage` if not provided.
        homepage "https://example.com"
        // Version tracker username.
        // Secret value: "`versionTrackerHomepage`".
        author "Username"
        // Version Tracker password.
        // Secret value: "`versionTrackerKey`".
        uid "00000000-0000-0000-0000-000000000000"
    }
    // Discord Webhook .
    webhook {
        // Enable webhook sending.
        enabled true
        // Curse project ID, used to generate the file url.
        curseId "254987"
        // Webhook URL.
        // Secret value: "`discordCFWebhook`".
        url ""
        // Avatar that the webhook should use.
        avatarUrl "https://media.forgecdn.net/avatars/130/894/636463564739010146.png"
    }
}
```