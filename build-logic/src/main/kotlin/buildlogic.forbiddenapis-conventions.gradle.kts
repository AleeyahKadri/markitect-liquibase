plugins {
    id("de.thetaphi.forbiddenapis")
}

forbiddenApis {
    bundledSignatures =
        setOf(
            "commons-io-unsafe-2.20.0",
            "jdk-deprecated",
            "jdk-internal",
            "jdk-non-portable",
            "jdk-reflection",
            "jdk-system-out",
            "jdk-unsafe",
        )
    ignoreSignaturesOfMissingClasses = true
}
