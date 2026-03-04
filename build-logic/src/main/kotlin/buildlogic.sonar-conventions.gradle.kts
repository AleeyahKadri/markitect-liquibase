plugins {
    id("org.sonarqube")
}

sonar {
    properties {
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            project(":markitect-liquibase-coverage").layout.buildDirectory
                .file("reports/jacoco/testCodeCoverageReport/testCodeCoverageReport.xml")
                .get().asFile.path,
        )
        property("sonar.sourceEncoding", "UTF-8")
    }
}
