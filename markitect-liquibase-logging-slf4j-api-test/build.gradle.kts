plugins {
    id("buildlogic.java-conventions")
}

dependencies {
    testImplementation(project(":markitect-liquibase-logging"))
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test) {
        exclude(group = "ch.qos.logback", module = "logback-classic")
    }
    testRuntimeOnly(libs.org.slf4j.slf4j.simple)
}

description = "Markitect Liquibase Logging - SLF4J API Tests"
