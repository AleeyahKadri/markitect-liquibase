plugins {
    id("buildlogic.spotless-conventions")
    id("buildlogic.sonar-conventions")
    idea
}

group = "dev.markitect.liquibase"
version = providers.gradleProperty("version").get()

idea {
    module {
        excludeDirs.add(file("target"))
    }
}
