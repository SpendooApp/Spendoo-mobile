plugins {
    id("spendoo.kmp.library")
}

kotlin {
    android {
        namespace = "com.spendoo.logging"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.koin.core)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.firebase.crashlytics)
            }
        }
    }
}
