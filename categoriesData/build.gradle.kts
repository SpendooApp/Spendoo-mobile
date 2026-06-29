plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.categories.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.categoriesDomain)
                implementation(projects.identityDomain)
                implementation(projects.sharedDomain)
                implementation(projects.sharedData)
            }
        }
    }
}
