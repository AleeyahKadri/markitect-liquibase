plugins {
    id("buildlogic.node-conventions")
    id("com.diffplug.spotless")
}

val ci = providers.environmentVariable("CI").isPresent
val windows = providers.systemProperty("os.name").get().startsWith("Windows", ignoreCase = true)
val npmInstallCache = rootProject.layout.projectDirectory.dir(".gradle/spotless-npm-install-cache")
val npmrc = rootProject.file("config/spotless/.npmrc")

fun getNodeExecutable() = tasks.named<com.github.gradle.node.task.NodeSetupTask>("nodeSetup").map { 
    it.nodeDir.get().file(if (windows) "node.exe" else "bin/node") 
}

fun getNpmExecutable() = tasks.named<com.github.gradle.node.task.NodeSetupTask>("nodeSetup").map { 
    it.nodeDir.get().file(if (windows) "npm.cmd" else "bin/npm") 
}

spotless {
    format("csv") {
        target("src/**/*.csv")
        leadingTabsToSpaces(2)
        trimTrailingWhitespace()
        endWithNewline()
    }
    groovyGradle {
        leadingTabsToSpaces(4)
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        ktlint()
    }
    java {
        ratchetFrom("origin/main")
        target("src/**/*.java")
        licenseHeaderFile(rootProject.file("config/spotless/license-header-java"))
            .onlyIfContentMatches("(?m)^package dev\\.markitect[.;]")
        cleanthat()
            .version(libs.versions.cleanthat.get())
            .sourceCompatibility("17")
            .addMutator("SafeButNotConsensual")
            .addMutator("UnnecessarySemicolon")
            .excludeMutator("AvoidInlineConditionals")
            .excludeMutator("LambdaIsMethodReference")
            .excludeMutator("LiteralsFirstInComparisons")
            .excludeMutator("LocalVariableTypeInference")
        googleJavaFormat(libs.versions.google.java.format.get())
            .reflowLongStrings()
    }
    json {
        target(
            "src/**/*.json",
            "renovate.json5",
        )
        prettier(libs.versions.prettier.asProvider().get())
            .nodeExecutable(getNodeExecutable())
            .npmExecutable(getNpmExecutable())
            .npmInstallCache(npmInstallCache)
            .npmrc(npmrc)
            .config(
                mapOf(
                    "printWidth" to 0,
                    "singleQuote" to true,
                ),
            )
    }
    format("markdown") {
        target("*.md")
        leadingTabsToSpaces(4)
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("properties") {
        target(
            "src/**/*.properties",
            "gradle.properties",
        )
        replaceRegex("prePrettier", "(?m)^([ \\t]*[#!])", "# \$1")
        prettier(
            mapOf(
                "prettier" to libs.versions.prettier.asProvider().get(),
                "prettier-plugin-properties" to libs.versions.prettier.plugin.properties.get(),
            ),
        )
            .nodeExecutable(getNodeExecutable())
            .npmExecutable(getNpmExecutable())
            .npmInstallCache(npmInstallCache)
            .npmrc(npmrc)
            .config(
                mapOf(
                    "parser" to "dot-properties",
                    "plugins" to listOf("prettier-plugin-properties"),
                    "keySeparator" to "=",
                    "printWidth" to 0,
                ),
            )
        replaceRegex("postPrettier", "(?m)^# ", "")
    }
    format("toml") {
        target("gradle/**/*.toml")
        prettier(
            mapOf(
                "prettier" to libs.versions.prettier.asProvider().get(),
                "prettier-plugin-toml" to libs.versions.prettier.plugin.toml.get(),
            ),
        )
            .nodeExecutable(getNodeExecutable())
            .npmExecutable(getNpmExecutable())
            .npmInstallCache(npmInstallCache)
            .npmrc(npmrc)
            .config(
                mapOf(
                    "parser" to "toml",
                    "plugins" to listOf("prettier-plugin-toml"),
                ),
            )
    }
    format("xml") {
        target(
            "config/**/*.xml",
            "src/**/*.xml",
        )
        leadingTabsToSpaces(2)
        trimTrailingWhitespace()
        endWithNewline()
    }
    yaml {
        target(
            ".github/**/*.yml",
            "src/**/*.yaml",
            "src/**/*.yml",
        )
        leadingTabsToSpaces(2)
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("misc") {
        target(
            ".github/CODEOWNERS",
            "config/**/.npmrc",
            "config/**/*.txt",
            "src/**/META-INF/services/liquibase.*",
            "src/**/META-INF/services/org.assertj.core.configuration.Configuration",
            "src/**/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports",
            "src/**/*.txt",
            "*.txt",
            ".editorconfig",
            ".java-version",
            ".sdkmanrc",
        )
        leadingTabsToSpaces(2)
        trimTrailingWhitespace()
        endWithNewline()
    }
}

listOf("spotlessJson", "spotlessProperties", "spotlessToml").forEach { name ->
    tasks.named(name) {
        dependsOn(rootProject.tasks.named("nodeSetup"))
    }
}

if (!ci) {
    tasks.named("spotlessCheck") {
        dependsOn(tasks.named("spotlessApply"))
    }
}
