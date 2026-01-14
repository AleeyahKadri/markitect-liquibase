plugins {
    id("buildlogic.java-conventions")
}

dependencies {
    testImplementation(project(":markitect-liquibase-logging"))
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
}

description = "Markitect Liquibase Logging - SLF4J SPI Tests"
