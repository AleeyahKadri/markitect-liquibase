plugins {
    id("buildlogic.java-conventions")
    id("buildlogic.publishing-conventions")
}

dependencies {
    api(libs.org.liquibase.liquibase.core)
    compileOnly(libs.org.apache.logging.log4j.log4j.api)
    compileOnly(libs.org.projectlombok.lombok)
    compileOnly(libs.org.slf4j.slf4j.api)
    implementation(libs.com.google.guava.guava)

    testCompileOnly(libs.org.projectlombok.lombok)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
}

description = "Markitect Liquibase Logging"

extra["automaticModuleName"] = "dev.markitect.liquibase.logging"
