plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.statistics.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.statisticsDomain)
                implementation(projects.identityDomain)
                implementation(projects.sharedDomain)
                implementation(projects.sharedData)
            }
        }
    }
}
