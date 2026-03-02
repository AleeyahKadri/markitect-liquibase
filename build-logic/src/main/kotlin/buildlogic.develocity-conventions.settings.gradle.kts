plugins {
    id("com.gradle.develocity")
}

val ci = providers.environmentVariable("CI").isPresent

develocity {
    buildScan {
        if (ci) {
            termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
            termsOfUseAgree.set("yes")
        }
        publishing.onlyIf { false }
    }
}
