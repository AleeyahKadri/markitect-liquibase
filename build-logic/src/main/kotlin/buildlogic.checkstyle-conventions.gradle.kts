plugins {
    checkstyle
}

dependencies {
    checkstyle(libs.com.puppycrawl.tools.checkstyle)
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configProperties =
        mapOf(
            "org.checkstyle.google.suppressionfilter.config" to
                configDirectory.file("suppressions.xml").get().asFile.path,
            "org.checkstyle.google.suppressionxpathfilter.config" to
                configDirectory.file("xpath-suppressions.xml").get().asFile.path,
        )
    maxWarnings = 0
}

tasks.withType<Checkstyle>().configureEach {
    group = "verification"
}

tasks.register("checkstyle") {
    group = "verification"
    dependsOn(tasks.withType<Checkstyle>())
}
