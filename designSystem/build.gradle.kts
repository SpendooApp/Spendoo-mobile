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
                implementation(libs.kotlinx.datetime)
                
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)
                implementation(libs.coil.svg)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }
    }
}

android {
    namespace = "com.spendoo.designsystem"
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.poolingcontainer)
}
