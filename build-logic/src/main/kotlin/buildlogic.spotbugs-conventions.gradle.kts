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
  toolVersion.set(
    libs.versions.spotbugs
      .asProvider()
      .get(),
  )
}

tasks.withType<SpotBugsTask>().configureEach {
  reportLevel.set(Confidence.LOW)
  effort.set(Effort.MAX)
  excludeFilter.set(rootProject.file("config/spotbugs/exclude.xml"))
  reports {
    html.required.set(true)
    xml.required.set(true)
  }
}

tasks.register("spotbugs") {
  group = "verification"
  dependsOn(tasks.withType<SpotBugsTask>())
}
