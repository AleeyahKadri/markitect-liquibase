pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("buildlogic.develocity-conventions")
    id("buildlogic.foojay-toolchains-conventions")
}

rootProject.name = "markitect-liquibase-build"

dependencyResolutionManagement {
    repositories {
        mavenCentral() {
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

include(":markitect-liquibase-bom")
include(":markitect-liquibase-core")
include(":markitect-liquibase-coverage")
include(":markitect-liquibase-logging")
include(":markitect-liquibase-logging-jul-test")
include(":markitect-liquibase-logging-log4j-test")
include(":markitect-liquibase-logging-slf4j-api-test")
include(":markitect-liquibase-logging-slf4j-spi-test")
include(":markitect-liquibase-spring")
include(":markitect-liquibase-spring-boot-starter")
