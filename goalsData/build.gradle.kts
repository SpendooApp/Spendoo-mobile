plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.goals.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.goalsDomain)
                implementation(projects.identityDomain)
                implementation(projects.sharedData)
            }
        }
    }
}
