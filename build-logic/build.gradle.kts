plugins {
    `groovy-gradle-plugin`
    alias(libs.plugins.com.diffplug.spotless)
    alias(libs.plugins.com.github.node.gradle.node)
    idea
}

val ci = providers.environmentVariable("CI").isPresent
val windows = providers.systemProperty("os.name").get().startsWithIgnoreCase("Windows")
val nodeExecutable = nodeSetup.nodeDir.file(if (windows) "node.exe" else "bin/node")
val npmExecutable = nodeSetup.nodeDir.file(if (windows) "npm.cmd" else "bin/npm")
val npmInstallCache = rootProject.layout.projectDirectory.dir(".gradle/spotless-npm-install-cache")
val npmrc = rootProject.file("../config/spotless/.npmrc")

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(plugin(libs.plugins.com.diffplug.spotless))
    implementation(plugin(libs.plugins.com.github.node.gradle.node))
    implementation(plugin(libs.plugins.com.github.spotbugs))
    implementation(plugin(libs.plugins.de.thetaphi.forbiddenapis))
    implementation(plugin(libs.plugins.net.ltgt.errorprone))
    implementation(plugin(libs.plugins.net.ltgt.nullaway))
    implementation(plugin(libs.plugins.org.openrewrite.rewrite))
    implementation(plugin(libs.plugins.org.sonarqube))
}

node {
    workDir.set(rootProject.layout.projectDirectory.dir(".gradle/nodejs"))
    version.set(libs.versions.node.get())
    distBaseUrl.set(null as String?)
    download.set(true)
    enableTaskRules.set(false)
}

nodeSetup.configure {
    enabled = project == rootProject
}

listOf("npmInstall", "npmSetup", "pnpmInstall", "pnpmSetup", "yarn", "yarnSetup").forEach { name ->
    tasks.named(name) {
        enabled = false
    }
}

spotless {
    ratchetFrom("origin/main")
    kotlinGradle {
        target(
            "src/**/*.gradle.kts",
            "*.gradle.kts",
        )
        ktlint()
    }
    format("properties") {
        target("gradle.properties")
        replaceRegex("prePrettier", "(?m)^([ \\t]*[#!])", "# $1")
        prettier(
            mapOf(
                "prettier" to libs.versions.prettier.asProvider().get(),
                "prettier-plugin-properties" to libs.versions.prettier.plugin.properties.get(),
            ),
        )
            .nodeExecutable(nodeExecutable)
            .npmExecutable(npmExecutable)
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
}

tasks.named("spotlessProperties") {
    dependsOn(rootProject.tasks.nodeSetup)
}

if (!ci) {
    spotlessCheck.configure {
        dependsOn(spotlessApply)
    }
}

fun plugin(plugin: Provider<PluginDependency>): Provider<String> {
    return plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
}
