import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.SpotBugsTask

plugins {
    id("com.github.spotbugs")
}

dependencies {
    spotbugs(libs.com.github.spotbugs.spotbugs)
    spotbugsPlugins(libs.com.h3xstream.findsecbugs.findsecbugs.plugin)
}

spotbugs {
    toolVersion = libs.versions.spotbugs.asProvider().get()
}

tasks.withType<SpotBugsTask>().configureEach {
    reportLevel = Confidence.LOW
    effort = Effort.MAX
    excludeFilter = rootProject.file("config/spotbugs/exclude.xml")
    reports {
        create("html") {
            enabled = true
        }
        create("xml") {
            enabled = true
        }
    }
}

tasks.register("spotbugs") {
    group = "verification"
    dependsOn(tasks.withType<SpotBugsTask>())
}
