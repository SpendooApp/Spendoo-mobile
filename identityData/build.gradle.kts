plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.identity.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.identityDomain)
            }
        }
    }
}
