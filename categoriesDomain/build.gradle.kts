plugins {
    id("spendoo.kmp.feature.domain")
}

kotlin {
    android {
        namespace = "com.spendoo.categories.domain"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.sharedDomain)
            }
        }
    }
}
