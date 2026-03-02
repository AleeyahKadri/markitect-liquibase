import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    base
    id("jacoco-report-aggregation")
    id("buildlogic.common-conventions")
}

dependencies {
    jacocoAggregation(project(":markitect-liquibase-core"))
    jacocoAggregation(project(":markitect-liquibase-logging"))
    jacocoAggregation(project(":markitect-liquibase-logging-jul-test"))
    jacocoAggregation(project(":markitect-liquibase-logging-log4j-test"))
    jacocoAggregation(project(":markitect-liquibase-logging-slf4j-api-test"))
    jacocoAggregation(project(":markitect-liquibase-logging-slf4j-spi-test"))
    jacocoAggregation(project(":markitect-liquibase-spring"))
    jacocoAggregation(project(":markitect-liquibase-spring-boot-starter"))
}

reporting {
    reports {
        create<JacocoCoverageReport>("testCodeCoverageReport") {
            testSuiteName = "test"
        }
    }
}

tasks.check.configure {
    dependsOn(tasks.named("testCodeCoverageReport", JacocoReport::class))
}

description = "Markitect Liquibase Coverage"
