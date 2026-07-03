plugins {
    id("spendoo.kmp.feature.data")
    id("spendoo.kmp.room")
}

kotlin {
    android {
        namespace = "com.spendoo.offers.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.offersDomain)
                implementation(projects.scraperDomain)
                implementation(projects.identityDomain)
            }
        }
    }
}
