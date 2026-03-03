plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidLibrary)
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
            baseName = "DesignSystemKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                // Compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.activity.compose)
                implementation("io.coil-kt.coil3:coil-compose:3.0.0-alpha10")
                implementation(projects.designSystem)
                implementation(projects.identityDomain)
                implementation("com.google.accompanist:accompanist-placeholder:0.34.0")
                implementation("com.google.accompanist:accompanist-placeholder-material:0.34.0")
            }
        }

        commonTest {
            dependencies {

            }
        }

        androidMain {
            dependencies {

            }
        }


        iosMain {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.spendoo.identitypresentation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
compose {
    resources {
        publicResClass = true
        packageOfResClass = "com.spendoo.identitypresentation.generated.resources"
    }
}