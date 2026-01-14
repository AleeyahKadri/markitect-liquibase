rootProject.name = "build-logic"

dependencyResolutionManagement {
  repositories {
    mavenCentral {
      content {
        excludeModule("org.nodejs", "node")
      }
    }
    gradlePluginPortal {
      content {
        excludeModule("org.nodejs", "node")
      }
    }
    ivy {
      url = uri("https://nodejs.org/dist/")
      patternLayout {
        artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]")
      }
      metadataSources {
        artifact()
      }
      content {
        includeModule("org.nodejs", "node")
      }
    }
  }
  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}
