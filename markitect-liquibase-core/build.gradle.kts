plugins {
    id("buildlogic.java-conventions")
    id("buildlogic.publishing-conventions")
}

dependencies {
    api(project(":markitect-liquibase-logging"))
    api(libs.org.liquibase.liquibase.core)
    compileOnly(libs.org.projectlombok.lombok)
    implementation(libs.com.google.guava.guava)

    testCompileOnly(libs.org.projectlombok.lombok)
    testImplementation(libs.com.google.guava.guava)
    testImplementation(libs.org.junit.pioneer.junit.pioneer)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.jdbc.test)
    testImplementation(libs.org.testcontainers.testcontainers.junit.jupiter)
    testImplementation(libs.org.testcontainers.testcontainers.mssqlserver)
    testImplementation(libs.org.testcontainers.testcontainers.postgresql)
    testRuntimeOnly(libs.com.fasterxml.jackson.core.jackson.databind)
    testRuntimeOnly(libs.com.h2database.h2)
    testRuntimeOnly(libs.com.microsoft.sqlserver.mssql.jdbc)
    testRuntimeOnly(libs.org.hsqldb.hsqldb)
    testRuntimeOnly(libs.org.junit.pioneer.junit.pioneer) {
        capabilities {
            requireCapability("org.junit-pioneer:junit-pioneer-jackson")
        }
    }
    testRuntimeOnly(libs.org.postgresql.postgresql)
}

sonar {
    properties {
        property(
            "sonar.cpd.exclusions",
            listOf(
                "src/main/java/dev/markitect/liquibase/change/CreateSchemaChange.java",
                "src/main/java/dev/markitect/liquibase/change/DropSchemaChange.java",
                "src/main/java/dev/markitect/liquibase/change/MarkitectInsertDataChangeMssql.java",
                "src/main/java/dev/markitect/liquibase/change/MarkitectLoadDataChangeMssql.java",
                "src/main/java/dev/markitect/liquibase/change/MarkitectLoadUpdateDataChangeMssql.java",
                "src/main/java/dev/markitect/liquibase/database/h2/MarkitectH2Database.java",
                "src/main/java/dev/markitect/liquibase/database/hsqldb/MarkitectHsqlDatabase.java",
                "src/main/java/dev/markitect/liquibase/database/mssql/MarkitectMssqlDatabase.java",
                "src/main/java/dev/markitect/liquibase/database/postgresql/MarkitectPostgresDatabase.java",
                "src/main/java/dev/markitect/liquibase/precondition/CatalogExistsPrecondition.java",
                "src/main/java/dev/markitect/liquibase/precondition/SchemaExistsPrecondition.java",
                "src/main/java/dev/markitect/liquibase/sqlgenerator/MarkitectInsertOrUpdateGeneratorHsql.java",
                "src/main/java/dev/markitect/liquibase/sqlgenerator/MarkitectInsertOrUpdateGeneratorPostgres.java",
                "src/main/java/dev/markitect/liquibase/statement/CreateSchemaStatement.java",
                "src/main/java/dev/markitect/liquibase/statement/DropSchemaStatement.java",
                "src/main/java/dev/markitect/liquibase/statement/SchemaExistsStatement.java",
            ).joinToString(","),
        )
    }
}

description = "Markitect Liquibase Core"

extra["automaticModuleName"] = "dev.markitect.liquibase.core"
