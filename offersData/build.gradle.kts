plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.offers.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.offersDomain)
            }
        }
    }
}
