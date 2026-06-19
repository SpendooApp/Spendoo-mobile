plugins {
    `kotlin-dsl`
}

group = "com.spendoo.convention"

dependencies {
    // We hardcode these versions here to avoid bootstrap issues with the version catalog 
    // in the build-logic's own build script. The convention plugins themselves 
    // will still use the version catalog at runtime.
    compileOnly("com.android.tools.build:gradle:9.2.1")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.21")
    compileOnly("org.jetbrains.compose:compose-gradle-plugin:1.11.0")
}

gradlePlugin {
    plugins {
        register("kmpLibrary") {
            id = "spendoo.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("kmpComposeLibrary") {
            id = "spendoo.kmp.compose.library"
            implementationClass = "KmpComposeLibraryConventionPlugin"
        }
        register("kmpFeatureApi") {
            id = "spendoo.kmp.feature.api"
            implementationClass = "KmpFeatureApiConventionPlugin"
        }
        register("kmpFeaturePresentation") {
            id = "spendoo.kmp.feature.presentation"
            implementationClass = "KmpFeaturePresentationConventionPlugin"
        }
        register("kmpFeatureData") {
            id = "spendoo.kmp.feature.data"
            implementationClass = "KmpFeatureDataConventionPlugin"
        }
        register("kmpFeatureDomain") {
            id = "spendoo.kmp.feature.domain"
            implementationClass = "KmpFeatureDomainConventionPlugin"
        }
        register("kmpApplication") {
            id = "spendoo.kmp.application"
            implementationClass = "KmpApplicationConventionPlugin"
        }
    }
}
