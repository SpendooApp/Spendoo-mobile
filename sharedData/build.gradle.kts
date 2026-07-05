plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.shared.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.sharedDomain)
                implementation(libs.koin.core)
            }
        }
    }
}
