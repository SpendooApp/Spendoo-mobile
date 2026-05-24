import org.jetbrains.compose.resources.ResourcesExtension

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
}

compose {
    resources {
        generateResClass = ResourcesExtension.ResourceClassGeneration.Never
    }
}

kotlin {
    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets

    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "IdentityPresentationKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                // Compose
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.ui)
                implementation(libs.compose.resources)
                implementation(libs.compose.ui.tooling.preview)

                implementation(libs.kotlinx.datetime)

                implementation(libs.coil.compose)

                implementation(projects.designSystem)
                implementation(projects.identityDomain)
                implementation(projects.identityApi)
                implementation(projects.homeApi)

                implementation(libs.bundles.koin)

                // Navigation
                implementation(libs.androidx.navigation.compose)
            }
        }

        commonTest {
            dependencies {

            }
        }

        androidMain {
            dependencies {
                implementation(libs.compose.preview)
                implementation(libs.androidx.activity.compose)
            }
        }


        iosMain {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.spendoo.identity.presentation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
}