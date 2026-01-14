plugins {
  id("buildlogic.node-conventions")
  id("com.diffplug.spotless")
}

val ci = providers.environmentVariable("CI").isPresent
val windows = providers.systemProperty("os.name").get().startsWith("Windows", ignoreCase = true)
val npmInstallCache = rootProject.layout.projectDirectory.dir(".gradle/spotless-npm-install-cache")
val npmrc = rootProject.file("config/spotless/.npmrc")

spotless {
  format("csv") {
    target("src/**/*.csv")
    leadingTabsToSpaces(2)
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
    googleJavaFormat(
      libs.versions.google.java.format
        .get(),
    ).reflowLongStrings()
  }
  format("markdown") {
    target("*.md")
    leadingTabsToSpaces(4)
    trimTrailingWhitespace()
    endWithNewline()
  }
  pom {
    target(
      "src/**/pom.xml",
      "pom.xml",
    )
    targetExclude("src/main/resources/archetype-resources/")
    sortPom(libs.versions.sortpom.get())
      .lineSeparator("\n")
      .expandEmptyElements(false)
      .indentAttribute("schemaLocation")
      .quiet(true)
  }
  format("xml") {
    target(
      "config/**/*.xml",
      "src/**/*.xml",
    )
    targetExclude("src/**/pom.xml")
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
      "src/main/resources/archetype-resources/**/pom.xml",
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

afterEvaluate {
  val nodeExecutable =
    tasks.nodeSetup
      .get()
      .nodeDir
      .file(if (windows) "node.exe" else "bin/node")
      .get()
      .asFile
  val npmExecutable =
    tasks.nodeSetup
      .get()
      .nodeDir
      .file(if (windows) "npm.cmd" else "bin/npm")
      .get()
      .asFile

  spotless {
    json {
      target(
        "src/**/*.json",
        "renovate.json5",
      )
      prettier(
        libs.versions.prettier
          .asProvider()
          .get(),
      ).nodeExecutable(nodeExecutable)
        .npmExecutable(npmExecutable)
        .npmInstallCache(npmInstallCache)
        .npmrc(npmrc)
        .config(
          mapOf(
            "printWidth" to 0,
            "singleQuote" to true,
          ),
        )
    }
    format("properties") {
      target(
        "src/**/*.properties",
        "gradle.properties",
      )
      replaceRegex("prePrettier", "(?m)^([ \\t]*[#!])", "# $1")
      prettier(
        mapOf(
          "prettier" to
            libs.versions.prettier
              .asProvider()
              .get(),
          "prettier-plugin-properties" to
            libs.versions.prettier.plugin.properties
              .get(),
        ),
      ).nodeExecutable(nodeExecutable)
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
    format("toml") {
      target("gradle/**/*.toml")
      prettier(
        mapOf(
          "prettier" to
            libs.versions.prettier
              .asProvider()
              .get(),
          "prettier-plugin-toml" to
            libs.versions.prettier.plugin.toml
              .get(),
        ),
      ).nodeExecutable(nodeExecutable)
        .npmExecutable(npmExecutable)
        .npmInstallCache(npmInstallCache)
        .npmrc(npmrc)
        .config(
          mapOf(
            "parser" to "toml",
            "plugins" to listOf("prettier-plugin-toml"),
          ),
        )
    }
  }

  listOf("spotlessJson", "spotlessProperties", "spotlessToml").forEach { name ->
    tasks.named(name) {
      dependsOn(rootProject.tasks.nodeSetup)
    }
  }
}

if (!ci) {
  tasks.spotlessCheck.configure {
    dependsOn(tasks.spotlessApply)
  }
}
