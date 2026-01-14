plugins {
    id("buildlogic.java-platform-conventions")
    id("buildlogic.publishing-conventions")
}

dependencies {
    constraints {
        api(project(":markitect-liquibase-core"))
        api(project(":markitect-liquibase-logging"))
        api(project(":markitect-liquibase-spring"))
        api(project(":markitect-liquibase-spring-boot-starter"))
    }
}

description = "Markitect Liquibase BOM"
