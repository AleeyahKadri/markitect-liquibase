plugins {
    jacoco
    `java-library`
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

dependencies {
    jacocoAgent(libs.org.jacoco.org.jacoco.agent)
    jacocoAnt(libs.org.jacoco.org.jacoco.ant)
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

tasks.jacocoTestReport {
    enabled = false
}
