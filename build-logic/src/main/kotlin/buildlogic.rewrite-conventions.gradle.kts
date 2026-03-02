plugins {
    id("org.openrewrite.rewrite")
}

dependencies {
    rewrite(libs.org.openrewrite.recipe.rewrite.static.analysis)
}

rewrite {
    activeRecipe("org.openrewrite.staticanalysis.NeedBraces")
    activeStyle("org.openrewrite.java.GoogleJavaFormat")
}
