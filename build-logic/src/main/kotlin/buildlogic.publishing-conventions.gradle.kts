plugins {
    `maven-publish`
    signing
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            plugins.withId("java-library") {
                from(components["java"])
            }
            plugins.withId("java-platform") {
                from(components["javaPlatform"])
            }
            pom {
                name.set(artifactId)
                description.set(providers.provider { project.description })
                url.set("https://github.com/markitect-dev/markitect-liquibase")
                inceptionYear.set("2023")
                organization {
                    name.set("Markitect")
                    url.set("https://github.com/markitect-dev")
                }
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        name.set("Mark Chesney")
                        url.set("https://github.com/mches")
                        organization.set("Markitect")
                        organizationUrl.set("https://github.com/markitect-dev")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/markitect-dev/markitect-liquibase.git")
                    developerConnection.set("scm:git:ssh://git@github.com/markitect-dev/markitect-liquibase.git")
                    url.set("https://github.com/markitect-dev/markitect-liquibase/tree/main")
                }
            }
        }
    }
    repositories {
        val isSnapshot = providers.provider { project.version.toString().endsWith("-SNAPSHOT") }
        val mavenAllowInsecureProtocol = providers.gradleProperty("mavenAllowInsecureProtocol").map { it.toBoolean() }
        val mavenReleaseRepositoryUrl = providers.gradleProperty("mavenReleaseRepositoryUrl")
        val mavenSnapshotRepositoryUrl = providers.gradleProperty("mavenSnapshotRepositoryUrl")
        val mavenRepositoryUrl = if (isSnapshot.get()) mavenSnapshotRepositoryUrl else mavenReleaseRepositoryUrl
        if (mavenRepositoryUrl.isPresent) {
            maven {
                name = "maven"
                url = uri(mavenRepositoryUrl.get())
                isAllowInsecureProtocol = mavenAllowInsecureProtocol.getOrElse(isAllowInsecureProtocol)
                credentials(PasswordCredentials::class)
            }
        }
    }
}

signing {
    val signingKeyId = providers.gradleProperty("signingKeyId")
    val signingKey = providers.gradleProperty("signingKey")
    val signingPassword = providers.gradleProperty("signingPassword")
    if (signingKey.isPresent && signingPassword.isPresent) {
        useInMemoryPgpKeys(signingKeyId.orNull, signingKey.get(), signingPassword.get())
    } else {
        useGpgCmd()
    }
    sign(publishing.publications["maven"])
}
