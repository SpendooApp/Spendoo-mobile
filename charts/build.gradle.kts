plugins {
    id("spendoo.kmp.compose.library")
    alias(libs.plugins.androidLint) apply false
}

kotlin {
    android {
        namespace = "com.spendoo.charts"
        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.compose.material)
            }
        }
    }
}
