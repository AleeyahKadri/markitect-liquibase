plugins {
    id("org.openrewrite.rewrite")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

dependencies {
    rewrite(libs.org.openrewrite.recipe.rewrite.static.analysis)
}

rewrite {
    activeRecipe("org.openrewrite.staticanalysis.NeedBraces")
    activeStyle("org.openrewrite.java.GoogleJavaFormat")
}
