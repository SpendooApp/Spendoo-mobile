import org.jetbrains.compose.resources.ResourcesExtension

plugins {
    id("spendoo.kmp.compose.library")
    alias(libs.plugins.androidLint) apply false
}

compose {
    resources {
        publicResClass = true
    }
}

kotlin {
    android {
        namespace = "com.spendoo.designsystem"
        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.datetime)
                
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)
                implementation(libs.coil.svg)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.poolingcontainer)
            }
        }
    }
}

dependencies {
    "androidRuntimeClasspath"(libs.compose.ui.tooling)
}
