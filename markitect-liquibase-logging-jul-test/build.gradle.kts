plugins {
    id("buildlogic.java-conventions")
}

dependencies {
    testImplementation(project(":markitect-liquibase-logging"))
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    testRuntimeOnly(libs.org.apache.logging.log4j.log4j.to.slf4j)
    testRuntimeOnly(libs.org.slf4j.slf4j.jdk14)
}

description = "Markitect Liquibase Logging - JUL Tests"
