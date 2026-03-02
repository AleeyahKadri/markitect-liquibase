plugins {
    jacoco
    `java-library`
}

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
