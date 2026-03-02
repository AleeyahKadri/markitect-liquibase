plugins {
    id("buildlogic.java-conventions")
    id("buildlogic.publishing-conventions")
}

dependencies {
    annotationProcessor(libs.org.springframework.boot.spring.boot.configuration.processor)

    api(project(":markitect-liquibase-spring"))
    api(libs.org.springframework.boot.spring.boot.starter.liquibase)
    implementation(libs.com.google.guava.guava)

    testImplementation(libs.com.h2database.h2)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.liquibase.test)
}

description = "Markitect Liquibase Spring Boot Starter"

extra["automaticModuleName"] = "dev.markitect.liquibase.spring.boot.starter"
