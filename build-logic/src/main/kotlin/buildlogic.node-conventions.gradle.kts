plugins {
    id("com.github.node-gradle.node")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
val verification by configurations.creating

dependencies {
    verification("org.nodejs:node:${libs.versions.node.get()}:darwin-arm64@tar.gz")
    verification("org.nodejs:node:${libs.versions.node.get()}:darwin-x64@tar.gz")
    verification("org.nodejs:node:${libs.versions.node.get()}:linux-arm64@tar.gz")
    verification("org.nodejs:node:${libs.versions.node.get()}:linux-x64@tar.gz")
    verification("org.nodejs:node:${libs.versions.node.get()}:win-arm64@zip")
    verification("org.nodejs:node:${libs.versions.node.get()}:win-x64@zip")
}

node {
    workDir.set(rootProject.layout.projectDirectory.dir(".gradle/nodejs"))
    version.set(libs.versions.node.get())
    distBaseUrl.set(null as String?)
    download.set(true)
    enableTaskRules.set(false)
}

tasks.named("nodeSetup") {
    enabled = project == rootProject
}

listOf("npmInstall", "npmSetup", "pnpmInstall", "pnpmSetup", "yarn", "yarnSetup").forEach { name ->
    tasks.named(name) {
        enabled = false
    }
}
