pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("com.gradle.develocity") version "4.3"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val ci = providers.environmentVariable("CI").isPresent

rootProject.name = "markitect-liquibase-build"

dependencyResolutionManagement {
    repositories {
        mavenCentral {
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
}

develocity {
    buildScan {
        if (ci) {
            termsOfUseUrl = "https://gradle.com/help/legal-terms-of-use"
            termsOfUseAgree = "yes"
        }
        publishing.onlyIf { false }
    }
}

include(":markitect-liquibase-bom")
include(":markitect-liquibase-core")
include(":markitect-liquibase-coverage")
include(":markitect-liquibase-logging")
include(":markitect-liquibase-logging-jul-test")
include(":markitect-liquibase-logging-log4j-test")
include(":markitect-liquibase-logging-slf4j-api-test")
include(":markitect-liquibase-logging-slf4j-spi-test")
include(":markitect-liquibase-parent")
include(":markitect-liquibase-spring")
include(":markitect-liquibase-spring-boot-archetype")
include(":markitect-liquibase-spring-boot-starter")
