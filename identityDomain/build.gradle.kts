plugins {
    id("spendoo.kmp.feature.domain")
}

kotlin {
    android {
        namespace = "com.spendoo.identity.domain"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.sharedDomain)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.core.ktx)
            }
        }
    }
}
