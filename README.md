# ModTemplate

A gradle plugin that provides helpers for common tasks, such as updating versions automatically, generating a changelog based on git commits and updating version trackers.

Not to be confused with https://github.com/jaredlll08/MultiLoader-Template

# Using

```groovy
plugins {
    id 'net.blamejared.modtemplate' version 'version_here'
}
```

Add the BlameJared maven to your `buildscript`.

# Options

```groovy
modTemplate {
    // Minecraft Version of this project.
    mcVersion "1.16.5"
    // Link to the CurseForge project, used to generate file links and for the update tracker.
    curseHomepage "https://www.curseforge.com/minecraft/mc-mods/slimyboyos"
    // Display name, used in the Discord webhook.
    displayName "SlimyBoyos"
    // name of the mod loader, used in the Discord webhook.
    // Optional, will not mention the mod loader in the discord webhook if not provided.
    modLoader "Forge"
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
        // Set by default, Used as the file name for the generated changelog.
        changelogFile "changelog.md"
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
        // Version Tracker project name.
        // Secret value: "`versionTrackerProjectName`"
        // Optional, will be replace by `projectName` from `modTemplate` if not provided.
        projectName "example"
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
}
```
