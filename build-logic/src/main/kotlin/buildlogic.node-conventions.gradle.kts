plugins {
  id("com.github.node-gradle.node")
}

node {
  workDir.set(rootProject.layout.projectDirectory.dir(".gradle/nodejs"))
  version.set(libs.versions.node.get())
  distBaseUrl.set(null as String?)
  download.set(true)
  enableTaskRules.set(false)
}

tasks.nodeSetup {
  enabled = project == rootProject
}

listOf("npmInstall", "npmSetup", "pnpmInstall", "pnpmSetup", "yarn", "yarnSetup").forEach { name ->
  tasks.named(name) {
    enabled = false
  }
}
