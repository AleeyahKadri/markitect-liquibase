plugins {
    id("buildlogic.java-conventions")
    id("buildlogic.publishing-conventions")
}

dependencies {
    api(project(":markitect-liquibase-core"))
    api(libs.org.springframework.spring.context)
    compileOnly(libs.org.projectlombok.lombok)
    implementation(libs.com.google.guava.guava)

    testCompileOnly(libs.org.projectlombok.lombok)
    testImplementation(libs.org.springframework.spring.jdbc)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testRuntimeOnly(libs.com.h2database.h2)
}

description = "Markitect Liquibase Spring Integration"

extra["automaticModuleName"] = "dev.markitect.liquibase.spring"
